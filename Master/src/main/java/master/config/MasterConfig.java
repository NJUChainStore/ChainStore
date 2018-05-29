package master.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MasterConfig {

    public static final int DIFFICULTY = 2;

    final Environment environment;

    private String localUrl;

    @Autowired
    public MasterConfig(Environment environment) {
        this.environment = environment;
        String port = environment.getProperty("server.port");
        this.localUrl = "http://localhost:" + port;
    }

    public String getLocalUrl() {
        return localUrl;
    }
}
