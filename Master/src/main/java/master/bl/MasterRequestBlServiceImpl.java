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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
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

        String url = databaseItem.getIp() + "/validate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authentication", databaseItem.getMasterToken());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ValidateResponse validateResponse = restTemplate.exchange(url, HttpMethod.GET, entity, ValidateResponse.class).getBody();
        if (!validateResponse.getResult()) {
            //改变存储机状态表
            databaseItem.setState(DatabaseState.Invalid);
            List<DatabaseItem> databaseItemList = TableManager.table.getDatabases();
            databaseItemList.set(index, databaseItem);
            TableManager.table.setDatabases(databaseItemList);

            askToSend(databaseItem, index, validateResponse.getStartingInvalidBlockIndex());
        } else {
            databaseItem.setState(DatabaseState.Available);
            List<DatabaseItem> databaseItemList = TableManager.table.getDatabases();
            databaseItemList.set(index, databaseItem);
            TableManager.table.setDatabases(databaseItemList);
        }
    }

    void askToSend(DatabaseItem databaseItem, int index, long blockStartIndex) {
        String senderAddress = "";
        String senderToken = "";
        int senderIndex = 0;
        List<DatabaseItem> databaseItems = TableManager.table.getDatabases();

        ArrayList<Integer> indexArr=new ArrayList<Integer>();
        for (int i = 0; i < databaseItems.size(); i++) {
            if (databaseItems.get(i).getState() == DatabaseState.Available) {
                //senderAddress = databaseItems.get(i).getIp();
                //senderToken = databaseItems.get(i).getAccessToken();
                //senderIndex = i;
                indexArr.add(i);
            }
        }

        int tempI=(int)(Math.random()*indexArr.size());
        //if(tempI==indexArr.size()&&tempI>0){tempI--;}
        senderAddress = databaseItems.get(indexArr.get(tempI)).getIp();
        senderToken = databaseItems.get(indexArr.get(tempI)).getAccessToken();
        senderIndex = indexArr.get(tempI);

        askToReceive(databaseItem, senderToken);
        DatabaseItem temp1 = databaseItems.get(index);
        temp1.setState(DatabaseState.Receiving);
        databaseItems.set(index, temp1);

        DatabaseItem temp2 = databaseItems.get(senderIndex);
        temp2.setState(DatabaseState.Sending);
        databaseItems.set(senderIndex, temp2);

        TableManager.table.setDatabases(databaseItems);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authentication", temp2.getMasterToken());
        HttpEntity<SendStartInfo> entity = new HttpEntity<>(new SendStartInfo(blockStartIndex, databaseItem.getIp()), headers);
        String url = senderAddress + "/send";
        SendStartedResponse sendStartedResponse = restTemplate.exchange(url, HttpMethod.POST, entity, SendStartedResponse.class).getBody();

    }

    void askToReceive(DatabaseItem databaseItem, String senderToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authentication", databaseItem.getMasterToken());
        HttpEntity<ReceiveStartInfo> entity = new HttpEntity<>(new ReceiveStartInfo(senderToken), headers);
        String url = databaseItem.getIp() + "/receive";
        ReceiveStartedResponse receiveStartedResponse = restTemplate.exchange(url, HttpMethod.POST, entity, ReceiveStartedResponse.class).getBody();
    }

}
