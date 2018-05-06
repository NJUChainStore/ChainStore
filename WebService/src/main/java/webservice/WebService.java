package webservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import webservice.parameters.SaveInfoParameters;
import webservice.response.SaveInfoResponse;

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class WebService {

    public static void main(String[] args) {
//        SpringApplication.run(WebService.class, args);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        for (int i = 0; i < 100000; i++) {
            HttpEntity<SaveInfoParameters> entity = new HttpEntity<>(new SaveInfoParameters("123"), headers);
            String url = "http://localhost:8080/saveInfo";
            SaveInfoResponse saveInfoResponse = restTemplate.exchange(url, HttpMethod.POST, entity, SaveInfoResponse.class).getBody();
            System.out.println(saveInfoResponse.getBlockIndex() + "/" + saveInfoResponse.getBlockOffset());
        }
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
