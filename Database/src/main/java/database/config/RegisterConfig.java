package database.config;

import database.model.GlobalData;
import database.model.RegisterParameters;
import database.model.Role;
import database.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterConfig {

    private static String masterUrl;

    public static String localUrl;

    private static int serverPort;

    public static void registerToMaster() {


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RegisterParameters> entity = new HttpEntity<>(new RegisterParameters(localUrl), headers);
        String url = masterUrl + "/register/" + Role.DATABASE;
        RegisterResponse registerResponse = restTemplate.exchange(url, HttpMethod.POST, entity, RegisterResponse.class).getBody();
        GlobalData.getInstance().setAccessToken(registerResponse.getAccessToken());
        GlobalData.getInstance().setMasterToken(registerResponse.getMasterToken());
    }

    public static void setLocalUrl() {
        String local = "http://localhost:";
        local = local + Integer.toString(serverPort);
        localUrl = local;
    }

    @LocalServerPort
    public static void setServerPort(int serverPort) {
        RegisterConfig.serverPort = serverPort;
    }

    @Value("${url.server}")
    public void setMasterUrl(String l) {
        masterUrl = l;
    }
}
