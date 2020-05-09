package io.github.wendergalan.desafio.config.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Swagger configuration.
 */
@Configuration
@EnableSwagger2
@Profile("development")
public class SwaggerConfiguration {

    /**
     * Person api 2 docket.
     *
     * @return the docket
     */
    @Bean
    public Docket personApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("desafio-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.wendergalan.desafio.api.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version("1").title("DESAFIO API").description("Documentação da API.").build());
    }
}
