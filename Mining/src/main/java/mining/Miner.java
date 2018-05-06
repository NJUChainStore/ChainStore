package mining;

import mining.model.Role;
import mining.parameters.RegisterParameters;
import mining.response.RegisterResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static mining.token.TokenManager.accessToken;
import static mining.token.TokenManager.masterToken;

@SpringBootApplication
@EnableSwagger2
public class Miner {

    private final static String masterAddress = "http://localhost:8080";

    public static void main(String[] args) {
        SpringApplication.run(Miner.class, args);
        register();

    }

    private static void register() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RegisterResponse> entity = restTemplate.postForEntity(masterAddress + "/register/MINER", new RegisterParameters("http://localhost:8079"), RegisterResponse.class);
        masterToken = entity.getBody().getMasterToken();
        accessToken = entity.getBody().getAccessToken();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Tag x00 API")
                .termsOfServiceUrl("")
                .contact(new Contact("Trap x00", "https://github.com/trapx00", "445073309@qq.com"))
                .version("1.0")
                .build();
    }
}
