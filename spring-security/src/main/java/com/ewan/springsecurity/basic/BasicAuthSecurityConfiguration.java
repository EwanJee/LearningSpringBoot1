package com.ewan.springsecurity.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class BasicAuthSecurityConfiguration {

    /**
     * HttpSecurity is the class which will help you to configure the filter chain.
     * It allows configuring web-based security for specific http requests.
     *
     * CSRF disable -> disable을 하지 않으면 어떤 요청이든 csrf token을 요구하게 된다.
     *
     * formLogin 주석 처리 -> 로그인폼이 없어짐
     *
     * session을 사용하지 않기 때문에 login, logout을 사용할 수 없다.
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(
                        session ->session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                ) // we don't need session ... stateless session policy
                //.formLogin(withDefaults())
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable()) // disable csrf
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // allow frame application to be embedded in iframe


        return http.build();
    }

    /**
     * Global CORS Configuration : Configure addCorsMappings callback method in WebMvcZConfigurer
     *
     * Local Configuration : @CrossOrigin - Allow from all origins
     *  ex) @CrossOrigin(origins = "http://localhost:3000") // allow from specific origin
     */

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        /**
//         *  good for test . -> store in memory */
//        UserDetails user = User.withUsername("user")
//                .password("{noop}dummy")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password("{noop}dummy")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        UserDetails user = User.withUsername("user")
                //.password("{noop}dummy")
                .password("dummy")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                //.password("{noop}dummy")
                .password("dummy")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("ADMIN", "USER", "HELLO")
                .build();

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(user);
        manager.createUser(admin);

        return manager;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
