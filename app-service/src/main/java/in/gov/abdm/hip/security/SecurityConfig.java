package in.gov.abdm.hip.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            RequestConverter converter,
            RequestManager manager) {
        AuthenticationWebFilter webFilter = new AuthenticationWebFilter(manager);
        webFilter.setServerAuthenticationConverter(converter);
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .anyExchange().authenticated()
                        .and()
                        .addFilterAt(webFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                        .httpBasic().disable()
                        .formLogin().disable()
                        .cors().disable()
                        .csrf().disable())
                .build();
    }
}