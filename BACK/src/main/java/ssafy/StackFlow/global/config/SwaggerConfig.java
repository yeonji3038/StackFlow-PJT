package ssafy.StackFlow.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        // Define security scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Create security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("StackFlow API")
                        .description("StackFlow API 명세서")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("StackFlow")
                                .email("rokmc17047200@gmail.com")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement);
    }

    @Bean
    public GroupedOpenApi signupApi() {
        return GroupedOpenApi.builder()
                .group("1. 회원관리")
                .pathsToMatch("/users/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }

    @Bean
    public GroupedOpenApi storeApi() {
        return GroupedOpenApi.builder()
                .group("2. 매장 관리")
                .pathsToMatch("/store/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("3. 상품 관리")
                .pathsToMatch("/product/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }

    @Bean
    public GroupedOpenApi retrievalApi() {
        return GroupedOpenApi.builder()
                .group("4. 입출고")
                .pathsToMatch("/retrieval/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }

    @Bean
    public GroupedOpenApi rtApi() {
        return GroupedOpenApi.builder()
                .group("5. RT")
                .pathsToMatch("/rt/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }


    @Bean
    public GroupedOpenApi noticeApi() {
        return GroupedOpenApi.builder()
                .group("6. 게시판")
                .pathsToMatch("/notice/**")
                .pathsToExclude("/**/dashboard/**")
                .build();
    }



}