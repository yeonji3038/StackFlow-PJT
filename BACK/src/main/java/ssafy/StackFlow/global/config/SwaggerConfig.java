package ssafy.StackFlow.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private Info apiInfo() {
        return new Info()
                .title("StackFlow API")
                .description("StackFlow API 명세서")
                .version("v1.0")
                .contact(new Contact().name("StackFlow")
                        .email("www.StackFlow.com")
                        .url("rokmc17047200@gmail.com"))
                .license(new License()
                        .name("License of API")
                        .url("API license URL"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/api"))
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(apiInfo());
    }

    // 매장 관리 그룹
    @Bean
    public GroupedOpenApi signupApi() {
        return GroupedOpenApi.builder()
                .group("매장 관리")
                .pathsToMatch("/users/**") // 매장 관리 경로만 문서화
                .build();
    }

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("관리자")
//                .pathsToMatch("/admin/**")
//                .build();
    }
