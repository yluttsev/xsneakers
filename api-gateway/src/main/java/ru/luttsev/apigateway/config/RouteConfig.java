package ru.luttsev.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth",
                        route -> route
                                .path("/api/auth/**")
                                .filters(filter -> filter.stripPrefix(1))
                                .uri("lb://auth-service"))
                .build();
    }
}
