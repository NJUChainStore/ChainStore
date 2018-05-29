package master.bl;

import master.blservice.MasterBlService;
import master.blservice.MasterRequestBlService;
import master.config.MasterConfig;
import master.exception.NoAvailableDatabaseException;
import master.getreponse.BlockAddedResponse;
import master.getreponse.BlockFoundResponse;
import master.getreponse.MineCompleteResponse;
import master.global.BufferManager;
import master.global.TableManager;
import master.global.entity.*;
import master.global.model.Block;
import master.parameters.SaveInfoParameters;
import master.request.MineParameter;
import master.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
public class MasterBlServiceImpl implements MasterBlService {
    private final MasterRequestBlService masterRequestBlService;

    @Autowired
    public MasterBlServiceImpl(MasterRequestBlService masterRequestBlService) {
        this.masterRequestBlService = masterRequestBlService;
    }


    /**
     * register a machine
     *
     * @param role
     * @return
     */
    @Override
    public RegisterResponse register(Role role, String ip) {
        // 生成一个accessToken，在自己的表里增加一项为这个accessToken对应一台机器，其角色由PathVariable来决定
        // 机器向主机请求时，需要带上accessToken，主机来检查这是哪一台机器
        // 再生成一个masterToken，并发给注册者，注册者通过masterToken判断一次请求是否由主机发出
        String accessToken = UUID.randomUUID().toString(); // 生成accessToken;
        String masterToken = UUID.randomUUID().toString();
        System.out.println(String.format("Register from %s accepted. Generated accessToken: %s, masterToken: %s", role, accessToken, masterToken));

        boolean isIpExists = TableManager.table.getDatabases().stream().anyMatch(x -> x.getIp().equals(ip));
        if (!isIpExists) {
            if (role.equals(Role.MINER)) {
                TableManager.table.setMinerAndBroadcast(new MinerItem(System.currentTimeMillis(), accessToken, masterToken, ip));
            } else {
                List<DatabaseItem> databaseItems = TableManager.table.getDatabases();
                databaseItems.add(new DatabaseItem(System.currentTimeMillis(), masterToken, accessToken, DatabaseState.Available, new Date(), ip));
                TableManager.table.setDatabasesAndBroadcast(databaseItems);
            }
        }
        return new RegisterResponse(accessToken, masterToken);
    }

    /**
     * send complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public SendCompleteReceivedResponse sendComplete(String id) {
        setDatabaseState(id, DatabaseState.Available);
        return new SendCompleteReceivedResponse(setDatabaseState(id, DatabaseState.Available));
    }

    /**
     * receive complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public ReceiveCompleteReceivedResponse receiveComplete(String id) {
        setDatabaseState(id, DatabaseState.Available);
        return new ReceiveCompleteReceivedResponse(setDatabaseState(id, DatabaseState.Available));
    }

    /**
     * find the block's info with the index and offset
     *
     * @param blockIndex
     * @param blockOffset
     * @return
     */
    @Override
    public FindBlockInfoResponse findBlockInfo(long blockIndex, int blockOffset) throws NoAvailableDatabaseException {
        if (blockIndex == TableManager.table.getNowBlockIndex()) {
            String value = BufferManager.buffer.getInfo(blockOffset);
            return new FindBlockInfoResponse(value);
        } else {
            return new FindBlockInfoResponse(findInfoFromDatabase(blockIndex, blockOffset));
        }
    }

    /**
     * save the info
     *
     * @param data
     * @return
     */
    @Override
    public SaveInfoResponse saveInfoAndBroadcast(String data) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        long blockIndex = TableManager.table.getNowBlockIndex();
        long blockOffset = BufferManager.buffer.getNowOffset();
        BufferManager.buffer.add(data);
        if (BufferManager.buffer.isFull()) {
            saveBlockToDatabase();
            for (String url : Table.masters) {
                if (!url.equals(TableManager.localUrl)) {
                    System.out.println("*******************************");
                    System.out.println(url);
                    System.out.println("*******************************");
                    String masterUrl = url + "/clearBuffer";
                    HttpEntity entity = new HttpEntity<>(null, headers);
                    try {
                        restTemplate.exchange(masterUrl, POST, entity, BufferClearResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            for (String url : Table.masters) {
                if (!url.equals(TableManager.localUrl)) {
                    System.out.println("*******************x************");
                    System.out.println(url);
                    System.out.println("*******************************");
                    String masterUrl = url + "/save";
                    HttpEntity entity = new HttpEntity<>(new SaveInfoParameters(data), headers);
                    try {
                        restTemplate.exchange(masterUrl, POST, entity, SaveInfoResponse.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new SaveInfoResponse(blockIndex, blockOffset);
    }

    /**
     * is the database all updated
     *
     * @param latestBlockIndex
     * @return
     */
    @Override
    public IsDatabaseUpdateResponse isDatabaseUpdate(int latestBlockIndex) {
        return new IsDatabaseUpdateResponse(TableManager.table.getNowBlockIndex() - 1 == latestBlockIndex);
    }

    /**
     * update self's buffer and table
     *
     * @param table
     * @return
     */
    @Override
    public UpdateSelfResponse updateTable(Table table) {
        TableManager.table = table;
        return new UpdateSelfResponse("success");
    }

    /**
     * save the info
     *
     * @param info
     * @return
     */
    @Override
    public SaveInfoResponse saveInfo(String info) {
        long blockIndex = TableManager.table.getNowBlockIndex();
        long blockOffset = BufferManager.buffer.getNowOffset();
        BufferManager.buffer.add(info);
        return new SaveInfoResponse(blockIndex, blockOffset);
    }

    /**
     * clear the master buffer
     *
     * @return
     */
    @Override
    public BufferClearResponse clearBuffer() {
        BufferManager.buffer.clear();
        return new BufferClearResponse("success");
    }

    private String setDatabaseState(String id, DatabaseState databaseState) {
        int index = 0;
        String masterToken = "";
        List<DatabaseItem> databaseItems = TableManager.table.getDatabases();
        for (DatabaseItem databaseItem : databaseItems) {
            if (databaseItem.getAccessToken().equals(id)) {
                databaseItem.setState(databaseState);
                databaseItems.set(index, databaseItem);
                masterToken = databaseItem.getMasterToken();
                break;
            }
            index++;
        }
        TableManager.table.setDatabasesAndBroadcast(databaseItems);
        return masterToken;
    }

    private String findInfoFromDatabase(long blockIndex, int blockOffset) throws NoAvailableDatabaseException {
        RestTemplate restTemplate = new RestTemplate();

        for (DatabaseItem databaseItem : TableManager.table.getDatabases()) {
            if (databaseItem.getState() == DatabaseState.Available) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                headers.add("Authentication", databaseItem.getMasterToken());
                HttpEntity<String> entity = new HttpEntity<>(null, headers);
                String url = databaseItem.getIp() + "/data" + "/" + blockIndex + "/" + blockOffset;
                BlockFoundResponse blockFoundResponse = restTemplate.exchange(url, GET, entity, BlockFoundResponse.class).getBody();
                return blockFoundResponse.getBase64Data();
            }
        }
        throw new NoAvailableDatabaseException();
    }

    @Async
    void saveBlockToDatabase() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authentication", TableManager.table.getMiner().getMasterToken());
        String mineUrl = TableManager.table.getMiner().getIp() + "/mine";
        List<String> nowInfos = BufferManager.buffer.getInfos();
        BufferManager.l2Buffer.setInfos(nowInfos);
        BufferManager.buffer.clear();
        HttpEntity<MineParameter> entity = new HttpEntity<>(new MineParameter(TableManager.table.getPreviousHash(), MasterConfig.DIFFICULTY, BufferManager.l2Buffer.getInfos()), headers);
        MineCompleteResponse mineCompleteResponseResponseEntity = restTemplate.exchange(mineUrl, POST, entity, MineCompleteResponse.class).getBody();
        BufferManager.l2Buffer.clear();
        TableManager.table.updateBlockIndex();

        for (DatabaseItem databaseItem : TableManager.table.getDatabases()) {
            if (isToBroadcast(databaseItem)) {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                headers.add("Authentication", databaseItem.getMasterToken());
                String databaseUrl = databaseItem.getIp() + "/data";
                HttpEntity<Block> blockHttpEntity = new HttpEntity<>(new Block(TableManager.table.getNowBlockIndex() - 1, mineCompleteResponseResponseEntity.getPreviousHash(), mineCompleteResponseResponseEntity.getHash(), mineCompleteResponseResponseEntity.getTimestamp(), mineCompleteResponseResponseEntity.getNonce(), mineCompleteResponseResponseEntity.getDifficulty(), mineCompleteResponseResponseEntity.getBase64Data()), headers);
                BlockAddedResponse blockAddedResponse = restTemplate.exchange(databaseUrl, POST, blockHttpEntity, BlockAddedResponse.class).getBody();
                TableManager.table.setPreviousHashAndBroadcast(mineCompleteResponseResponseEntity.getHash());
            }
        }
    }

    private boolean isToBroadcast(DatabaseItem databaseItem) {
        return databaseItem.getState() == DatabaseState.Available || databaseItem.getState() == DatabaseState.Sending || databaseItem.getState() == DatabaseState.Validating;
    }

}
