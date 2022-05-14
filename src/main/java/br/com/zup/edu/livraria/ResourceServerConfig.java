package br.com.zup.edu.livraria;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors()
            .and()
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .logout().disable()
                .headers().frameOptions().deny()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(GET, "/api/livros/**").hasAuthority("SCOPE_livros:read")
                    .antMatchers(POST, "/api/livros").hasAuthority("SCOPE_livros:write")
                    .antMatchers(GET, "/api/autores/**").hasAuthority("SCOPE_autores:read")
                    .antMatchers(POST, "/api/autores").hasAuthority("SCOPE_autores:write")
                    .antMatchers(DELETE, "/api/autores/**").hasAuthority("SCOPE_autores:write")
                .anyRequest()
                    .authenticated()
            .and()
                .oauth2ResourceServer()
                    .jwt(); // atencao: necessario pois estamos sobrescrevendo a conf do .properties
        ;
        // @formatter:on
    }
}
