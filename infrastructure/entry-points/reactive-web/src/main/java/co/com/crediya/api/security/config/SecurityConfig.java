package co.com.crediya.api.security.config;

import co.com.crediya.api.constant.OpenApiEndpoint;
import co.com.crediya.api.rest.reportmetric.constant.ReportMetricEndpoint;
import co.com.crediya.api.security.handler.AuthenticationHandler;
import co.com.crediya.api.security.handler.AuthorizationHandler;
import co.com.crediya.model.auth.constants.RoleConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationWebFilter jwtAuthenticationWebFilter;
    private final AuthenticationHandler authenticationHandler;
    private final AuthorizationHandler authorizationHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                OpenApiEndpoint.API_DOCS.getPath(),
                                OpenApiEndpoint.SWAGGER_UI.getPath(),
                                OpenApiEndpoint.SWAGGER_UI_RESOURCES.getPath(),
                                OpenApiEndpoint.WEB_JARS.getPath()
                        ).permitAll()
                        .pathMatchers(ReportMetricEndpoint.GET_LOAN_APPROVALS.getPath())
                        .hasRole(RoleConstant.ADMINISTRATOR.getName())
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .accessDeniedHandler(authorizationHandler)
                                .authenticationEntryPoint(authenticationHandler)
                )
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
