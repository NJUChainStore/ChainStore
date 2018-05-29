package master.global.entity;

import master.global.TableManager;
import master.parameters.UpdateSelfParameters;
import master.response.UpdateSelfResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

public class Table {
    private long nowBlockIndex = 0;
    private String previousHash = "0";
    private MinerItem miner;
    private List<DatabaseItem> databases = Collections.synchronizedList(new ArrayList<>());
    public static List<String> masters = new ArrayList<>();

    static {
        masters.add("http://localhost:8000");
        masters.add("http://localhost:8001");
        masters.add("http://localhost:8002");
    }

    public MinerItem getMiner() {
        return miner;
    }

    public void setMiner(MinerItem miner) {
        this.miner = miner;
    }

    public void setMinerAndBroadcast(MinerItem miner) {
        this.miner = miner;
        broadcastSelf();
    }

    public List<DatabaseItem> getDatabases() {
        return databases;
    }

    public void setDatabases(List<DatabaseItem> databases) {
        this.databases = databases;
    }

    public void setDatabasesAndBroadcast(List<DatabaseItem> databases) {
        this.databases = databases;
        broadcastSelf();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setPreviousHashAndBroadcast(String previousHash) {
        this.previousHash = previousHash;
        broadcastSelf();
    }

    public long getNowBlockIndex() {
        return nowBlockIndex;
    }

    public void setNowBlockIndex(long nowBlockIndex) {
        this.nowBlockIndex = nowBlockIndex;
    }

    public void updateBlockIndexWithoutRequest() {
        nowBlockIndex++;
    }

    public void updateBlockIndex() {
        nowBlockIndex++;
        broadcastSelf();
    }

    private void broadcastSelf() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        for (String url : Table.masters) {
            if (!url.equals(TableManager.localUrl)) {
                System.out.println("*******************************");
                System.out.println(url);
                System.out.println("*******************************");
                String masterUrl = url + "/updateTable";
                HttpEntity entity = new HttpEntity<>(new UpdateSelfParameters(this), headers);
                try {
                    restTemplate.exchange(masterUrl, POST, entity, UpdateSelfResponse.class).getBody();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
