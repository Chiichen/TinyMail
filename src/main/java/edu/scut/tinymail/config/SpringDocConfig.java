package edu.scut.tinymail.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc API文档相关配置
 * Created by macro on 2022/3/4.
 */

@Configuration
public class SpringDocConfig {

//    @Bean
//    public OpenAPI mallTinyOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("Mall-Tiny API")
//                        .description("SpringDoc API 演示")
//                        .version("v1.0.0")
//                        .license(new License().name("Apache 2.0").url("https://github.com/macrozheng/mall-learning")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("SpringBoot实战电商项目mall（50K+Star）全套文档")
//                        .url("http://www.macrozheng.com"))
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(new Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                                new SecurityScheme()
//                                        .name(SECURITY_SCHEME_NAME)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
//    }


    private License license() {
        return new License()
                .name("MIT")
                .url("https://opensource.org/licenses/MIT");
    }

    private Info info() {
        return new Info()
                .title("Ya API")
                .description("A test project for Mr.Ya.")
                .version("v1.0.0")
                .license(license());
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("这是一个额外的描述。")
                .url("https://shijizhe.github.io/");
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(info())
                .externalDocs(externalDocumentation());
    }
}


//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("user")
//                .pathsToMatch("/api/user/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("mail")
//                .pathsToMatch("/api/mail/**")
//                .build();
//
//    }
