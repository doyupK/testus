package com.testus.testus.config;

import com.testus.testus.common.response.exception.Code;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@OpenAPIDefinition(
//        info = @Info(title = "Testus",
//                description = "Testus API 명세",
//                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Testus API v1")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        StringBuilder API_DESCRIPTION =
                new StringBuilder(" <h1>Testus 서비스</h1> <br> " +
                        " <h3><B><status code 정의></B></h3> ");
        for (Code customErrorCode : Code.values()) {
            API_DESCRIPTION.append(customErrorCode.getCode()).append(" : ").append(customErrorCode.getMessage()).append("<br>");
        }
        Server local = new Server();
        local.setDescription("localhost Test");
        local.setUrl("http://localhost:8080/");

        Server server = new Server();
        server.setDescription("server");
        server.setUrl("http://54.180.123.43:8080");

        List<Server> serverList = new ArrayList<>();
        serverList.add(local);
        serverList.add(server);


        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("TESTUS API Docs")
                        .description(API_DESCRIPTION.toString())
                        .version("1.0"))
                .servers(serverList)
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                        .addSecuritySchemes("refreshToken", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("refreshToken")
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("Authorization")
                        .addList("refreshToken")
                )
                ;

    }



}