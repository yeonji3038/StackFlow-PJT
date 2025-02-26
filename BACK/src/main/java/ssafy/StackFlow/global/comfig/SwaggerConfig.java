//package ssafy.StackFlow.global.comfig;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
////    private ProcessHandle.Info apiInfo() {
////        return new ProcessHandle.Info()
////                .title("StackFlow API")
////                .description("StackFlow API 명세서")
////                .version("v1.0")
////                .contact(new Contact().name("StackFlow")
////                        .email("www.StackFlow.com")
////                        .url("rokmc17047200gmail.com"))
////                .license(new License()
////                        .name("License of API")
////                        .url("API license URL"));
////    }
////
////    private SecurityScheme createAPIKeyScheme() {
////        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
////                .bearerFormat("JWT")
////                .scheme("bearer");
////    }
////
////
////    @Bean
////    public GroupedOpenApi teacherApi() {
////        return GroupedOpenApi.builder()
////                .group("1. 교사")
////                .pathsToMatch("/teachers/**")
////                .pathsToExclude("/teachers/jobs/**", "/teachers/nations/**", "/**/dashboard/**")
////                .build();
////    }
////
////
////
////    @Bean
////    public GroupedOpenApi homeApi() {
////        return GroupedOpenApi.builder()
////                .group("3. 홈 화면")
////                .pathsToMatch("/**/dashboard/**")
////                .build();
////    }
////
////
////    @Bean
////    public GroupedOpenApi adminApi() {
////        return GroupedOpenApi.builder()
////                .group("0. 관리자")
////                .pathsToMatch("/admin/**")
////                .build();
////    }
//
//
//}
