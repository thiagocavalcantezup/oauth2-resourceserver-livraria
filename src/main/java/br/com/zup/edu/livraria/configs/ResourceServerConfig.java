package br.com.zup.edu.livraria.configs;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors()
            .and()
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .logout().disable()
                .requestCache().disable()
                .headers().frameOptions().deny()
            .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(POST, "/api/livros").hasAuthority("SCOPE_livros:write")
                .antMatchers(GET, "/api/livros/**").hasAuthority("SCOPE_livros:read")
                .antMatchers(POST, "/api/autores").hasAuthority("SCOPE_autores:write")
                .antMatchers(DELETE, "/api/autores/**").hasAuthority("SCOPE_autores:write")
                .antMatchers(GET, "/api/autores/**").hasAuthority("SCOPE_autores:read")
                .anyRequest().authenticated()
            .and()
                .oauth2ResourceServer().jwt();
        // @formatter:on

        return http.build();
    }

}
