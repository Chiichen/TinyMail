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


    private License license() {
        return new License()
                .name("MIT")
                .url("https://opensource.org/licenses/MIT");
    }

    private Info info() {
        return new Info()
                .title("Tiny Mail接口文档")
                .description("关于华南理工大学软件开发实训项目TinyMail的接口文档")
                .version("v1.0.0")
                .license(license());
    }

    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("有问题请致电")
                .url("https://www.google.com");
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(info())
                .externalDocs(externalDocumentation());
    }
}



