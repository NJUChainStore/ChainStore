package database.config;

import database.model.GlobalData;
import database.model.RegisterParameters;
import database.model.Role;
import database.response.RegisterResponse;
import database.util.PortUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RegisterConfig {
    @Value("${url.server}")
    private static String masterUrl;
    public static String localUrl;
    public  static void registerToMaster() {


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RegisterParameters> entity = new HttpEntity<>(new RegisterParameters(localUrl), headers);
        String url =masterUrl+"/register/" + Role.DATABASE;
        RegisterResponse registerResponse = restTemplate.exchange(url, HttpMethod.POST, entity, RegisterResponse.class).getBody();
        GlobalData.getInstance().setAccessToken(registerResponse.getAccessToken());
        GlobalData.getInstance().setMasterToken(registerResponse.getMasterToken());
    }

    public static void setLocalUrl(int portNum){
        String local="http://localhost:";
        //int portNum= PortUtil.getRandomPort();
        local=local+Integer.toString(portNum);
        localUrl=local;

    }


}
