package master.bl;

import master.blservice.MasterRequestBlService;
import master.getreponse.ReceiveStartedResponse;
import master.getreponse.SendStartedResponse;
import master.getreponse.ValidateResponse;
import master.global.TableManager;
import master.global.entity.DatabaseItem;
import master.global.entity.DatabaseState;
import master.request.ReceiveStartInfo;
import master.request.SendStartInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

            askToSend(databaseItem, index);
        }
    }

    void askToSend(DatabaseItem databaseItem, int index) {
        String senderAddress = "";
        String senderToken = "";
        int senderIndex = 0;
        List<DatabaseItem> databaseItems = TableManager.table.getDatabases();
        for (int i = 0; i < databaseItems.size(); i++) {
            if (databaseItems.get(i).getState() == DatabaseState.Available) {
                senderAddress = databaseItems.get(i).getIp();
                senderToken = databaseItems.get(i).getAccessToken();
                senderIndex = i;
            }
        }

        askToReceive(databaseItem, senderAddress);
        DatabaseItem temp1 = databaseItems.get(index);
        temp1.setState(DatabaseState.Receiving);
        databaseItems.set(index, temp1);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<SendStartInfo> entity = new HttpEntity<>(new SendStartInfo(databaseItem.getIp()), headers);
        String url = senderAddress + "/send";
        SendStartedResponse sendStartedResponse = restTemplate.exchange(url, HttpMethod.POST, entity, SendStartedResponse.class).getBody();

        DatabaseItem temp2 = databaseItems.get(index);
        temp2.setState(DatabaseState.Sending);
        databaseItems.set(index, temp2);

        TableManager.table.setDatabases(databaseItems);
    }

    void askToReceive(DatabaseItem databaseItem, String senderToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<ReceiveStartInfo> entity = new HttpEntity<>(new ReceiveStartInfo(senderToken), headers);
        String url = databaseItem.getIp() + "/receive";
        ReceiveStartedResponse receiveStartedResponse = restTemplate.exchange(url, HttpMethod.POST, entity, ReceiveStartedResponse.class).getBody();
    }

}