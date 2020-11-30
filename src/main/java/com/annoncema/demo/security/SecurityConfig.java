package com.annoncema.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] URLS_ACCESS =
            {"/login", "/register", "/ads/maroc/**", "/ads/**", "/ads**",
                    "/ads/search/**/**/**", "/ads/maroc/cp/**", "/ads/cp/**/**",
                    "/ads/categoryParents/images",

                    "/categoryParents",
                    "/categoryParents/**",
                    "/categories", "/categories/**", "/cities**", "/cities/**",
                    "/typeod", "/typepp",

                    "/users/**"

            };
    private final String[] URLS_ADMIN =
            { "/save**", "/update**", "/delete**/**", "/users**", "/adminads"
            };

    @Autowired
    private UserDetailsService userDetails;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // http.formLogin();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers(URLS_ACCESS).permitAll();
        http.authorizeRequests().antMatchers(URLS_ADMIN).hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
