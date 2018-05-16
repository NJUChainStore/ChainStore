package database.config;

import database.model.GlobalData;
import database.model.RegisterParameters;
import database.model.Role;
import database.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class RegisterConfig {

    final Environment environment;

    @Value("${url.server}")
    private String masterUrl;

    public String localUrl;

    public int serverPort;

    @Autowired
    public RegisterConfig(Environment environment) {
        this.environment = environment;
        String port = environment.getProperty("server.port");
        localUrl = "http://localhost:" + port;
        this.serverPort = Integer.parseInt(port);
    }

    public void registerToMaster() {


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<RegisterParameters> entity = new HttpEntity<>(new RegisterParameters(localUrl), headers);
        String url = masterUrl + "/register/" + Role.DATABASE;
        RegisterResponse registerResponse = restTemplate.exchange(url, HttpMethod.POST, entity, RegisterResponse.class).getBody();
        GlobalData.getInstance().setAccessToken(registerResponse.getAccessToken());
        GlobalData.getInstance().setMasterToken(registerResponse.getMasterToken());
    }
}
