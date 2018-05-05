package master.bl;

import master.blservice.MasterBlService;
import master.config.MasterConfig;
import master.exception.NoAvailableDatabaseException;
import master.getreponse.BlockAddedResponse;
import master.getreponse.BlockFoundResponse;
import master.getreponse.MineCompleteResponse;
import master.global.BufferManager;
import master.global.TableManager;
import master.global.entity.DatabaseItem;
import master.global.entity.DatabaseState;
import master.global.entity.MinerItem;
import master.global.entity.Role;
import master.global.model.Block;
import master.request.MineParameter;
import master.response.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MasterBlServiceImpl implements MasterBlService {
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

        if (role.equals(Role.MINER)) {
            TableManager.table.setMiner(new MinerItem(System.currentTimeMillis(), accessToken, masterToken, ip));
        } else {
            TableManager.table.getDatabases().add(new DatabaseItem(System.currentTimeMillis(), masterToken, accessToken, DatabaseState.Available, new Date(), ip));
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
    public FindBlockInfoResponse findBlockInfo(int blockIndex, int blockOffset) throws NoAvailableDatabaseException {
        if (blockIndex == TableManager.table.getNowBlockIndex()) {
            return new FindBlockInfoResponse(BufferManager.buffer.getInfo(blockOffset));
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
    public SaveInfoResponse saveInfo(String data) {
        BufferManager.buffer.add(data);
        long blockIndex = TableManager.table.getNowBlockIndex();
        long blockOffset = BufferManager.buffer.getNowOffset();
        BufferManager.l2Buffer.setInfos(BufferManager.buffer.getInfos());
        BufferManager.buffer.clear();
        if (BufferManager.buffer.isFull()) {
            saveBlockToDatabase();
        }
        return new SaveInfoResponse(blockIndex, blockOffset);
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
        TableManager.table.setDatabases(databaseItems);
        return masterToken;
    }

    private String findInfoFromDatabase(int blockIndex, int blockOffset) throws NoAvailableDatabaseException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        for (DatabaseItem databaseItem : TableManager.table.getDatabases()) {
            if (databaseItem.getState() == DatabaseState.Available) {
                HttpEntity<String> entity = new HttpEntity<>(null, headers);
                String url = databaseItem.getIp() + "/" + blockIndex + "/" + blockOffset;
                BlockFoundResponse blockFoundResponse = restTemplate.exchange(url, HttpMethod.GET, entity, BlockFoundResponse.class).getBody();
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

        String mineUrl = TableManager.table.getMiner().getIp();
        HttpEntity<MineParameter> entity = new HttpEntity<>(new MineParameter(TableManager.table.getPreviousHash(), MasterConfig.DIFFICULTY, BufferManager.l2Buffer.getInfos()), headers);
        MineCompleteResponse mineCompleteResponseResponseEntity = restTemplate.exchange(mineUrl, HttpMethod.POST, entity, MineCompleteResponse.class).getBody();
        BufferManager.l2Buffer.clear();
        TableManager.table.updateBlockIndex();

        for (DatabaseItem databaseItem : TableManager.table.getDatabases()) {
            if (isToBroadcast(databaseItem)) {
                HttpEntity<Block> blockHttpEntity = new HttpEntity<>(new Block(mineCompleteResponseResponseEntity.getPreviousHash(), mineCompleteResponseResponseEntity.getDifficulty(), mineCompleteResponseResponseEntity.getBase64Data(), mineCompleteResponseResponseEntity.getNonce(), mineCompleteResponseResponseEntity.getHash(), mineCompleteResponseResponseEntity.getTimestamp()), headers);
                BlockAddedResponse blockAddedResponse = restTemplate.exchange(databaseItem.getIp(), HttpMethod.POST, entity, BlockAddedResponse.class).getBody();
            }
        }
    }

    private boolean isToBroadcast(DatabaseItem databaseItem) {
        return databaseItem.getState() == DatabaseState.Available || databaseItem.getState() == DatabaseState.Sending || databaseItem.getState() == DatabaseState.Validating;
    }

}
