package com.andre.librarycompass.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userManagers(){
        UserDetails usuario = User.builder().username("cliente").password("{noop}cliente").roles("USER").build();
        UserDetails atendente = User.builder().username("atendente").password("{noop}atendente").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(usuario, atendente);
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurator ->
                configurator
                        // REST API
                        .requestMatchers(HttpMethod.GET, "/api/livros", "/api/livros/*").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/livros").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/livros/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/livros/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/livros/*").hasRole("ADMIN")

                        .requestMatchers("/api/livros/*/emprestar/*", "/api/livros/*/devolver").hasRole("ADMIN")

                        // MVC
                        .requestMatchers("/", "/livros").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/livros/**", "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/accessDenied").permitAll()

                        // ALLOW ACCESS CSS AND IMAGES
                        .requestMatchers("/css/*").permitAll()
                        .requestMatchers("/img/*").permitAll()
                );

        http.formLogin(form ->
                form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
        );

        http.logout(logout -> logout.permitAll());

        http.exceptionHandling(configurer ->
                configurer.accessDeniedPage("/accessDenied")
        );

        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
