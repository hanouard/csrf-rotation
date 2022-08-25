package com.example.csrfrotation.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurity {


    @Bean
    public SecurityWebFilterChain securityWebFilterChainDatabase(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                .httpBasic()
                .and()
                .authorizeExchange()
                .anyExchange()
                .authenticated().and().build();
    }

    @Bean
    public WebFilter addCsrfToken() {
        // workaround as explained here: https://github.com/spring-projects/spring-security/issues/5766
        return (exchange, next) -> {
            Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
            return csrfToken.doOnSuccess(token -> {})
                    .then(next.filter(exchange));
        };
    }
}
