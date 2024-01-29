package com.learning.webservices.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSecurityConfiguration {

    /**
     * 1. All requests should be authenticated
     * 2. If a request is not authenticated, a web page is shown
     * 3. CSRF -> POST, PUT
     */
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        http.httpBasic(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }*/

}
