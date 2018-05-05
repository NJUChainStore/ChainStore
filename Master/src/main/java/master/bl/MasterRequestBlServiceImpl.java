package master.bl;

import master.blservice.MasterRequestBlService;
import master.config.MasterConfig;
import master.getreponse.BlockAddedResponse;
import master.getreponse.MineCompleteResponse;
import master.getreponse.ValidateResponse;
import master.global.BufferManager;
import master.global.TableManager;
import master.global.entity.DatabaseItem;
import master.global.entity.DatabaseState;
import master.global.model.Block;
import master.request.MineParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MasterRequestBlServiceImpl implements MasterRequestBlService {
    /**
     * send request to ask database to validate itself
     *
     * @param databaseItem
     * @param index
     */
    @Override
    public void sendValidateRequest(DatabaseItem databaseItem, int index) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = databaseItem.getIp() + "/validate";
        ValidateResponse validateResponse = restTemplate.exchange(url, HttpMethod.GET, entity, ValidateResponse.class).getBody();
        if (!validateResponse.getResult()) {
            //改变存储机状态表
            databaseItem.setState(DatabaseState.Invalid);
            List<DatabaseItem> databaseItemList = TableManager.table.getDatabases();
            databaseItemList.set(index, databaseItem);
            TableManager.table.setDatabases(databaseItemList);


        }
    }

    @Async
    void askToSend() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = databaseItem.getIp() + "/validate";
        ValidateResponse validateResponse = restTemplate.exchange(url, HttpMethod.GET, entity, ValidateResponse.class).getBody();
    }

    @Async
    void askToReceive() {
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

}
