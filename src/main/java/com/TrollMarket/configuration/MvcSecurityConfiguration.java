package com.TrollMarket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) ->
                csrf.disable()
        ).authorizeHttpRequests((request) -> request
                .requestMatchers("/css/**", "/account/loginForm", "/account/registerForm",
                        "/account/register").permitAll()
                .requestMatchers("/shipper/**", "/admin/**", "/order/**").hasAuthority("Admin")
                .requestMatchers("/account/index").hasAnyAuthority("Seller", "Buyer")
                .requestMatchers("/product/merchandise").hasAuthority("Seller")
                .requestMatchers("/product/shop", "/cart/**").hasAuthority("Buyer")
                .anyRequest().authenticated()
        ).formLogin((form) -> form
                .loginPage("/account/loginForm")
                .loginProcessingUrl("/authenticating")
                .failureUrl("/account/loginForm")
        ).logout((logout) -> logout
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID").permitAll()
        );

        return http.build();
    }
}
