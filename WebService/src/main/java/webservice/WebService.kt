package webservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import webservice.db.BaseDatabaseFactory
import webservice.parameters.SaveInfoParameters
import webservice.response.SaveInfoResponse

@SpringBootApplication
@EnableSwagger2
@EnableAutoConfiguration(exclude = [(JacksonAutoConfiguration::class)])
open class WebService {

    @Bean
    open fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(javaClass.`package`.name))
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("ChainStore Web Service API")
                .termsOfServiceUrl("")
                .contact(Contact("Trap x00", "https://github.com/trapx00", "445073309@qq.com"))
                .version("1.0")
                .build()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            BaseDatabaseFactory.init()
            SpringApplication.run(WebService::class.java, *args)

        }
    }
}
