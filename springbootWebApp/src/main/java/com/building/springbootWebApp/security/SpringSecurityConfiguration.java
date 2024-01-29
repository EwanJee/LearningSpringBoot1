package com.building.springbootWebApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;


public class SpringSecurityConfiguration {
   //LDAP or Database
   //In Memory
//
//   InMemoryUserDetailsManager
//    InMemoryUserDetailsManager(UserDetails... users)

//    @Bean
//    public InMemoryUserDetailsManager createUserDetailsManager(){
//
//        UserDetails userDetails = createNewUser("user","password");
//        UserDetails userDetails2 = createNewUser("user2","password2");
//        return new InMemoryUserDetailsManager(userDetails,userDetails2);
//    }
//
//    private UserDetails createNewUser(String username, String password) {
//        Function<String, String> passwordEncoder
//                = input -> passwordEncoder().encode(input);
//
//        UserDetails userDetails = User.builder()
//                .passwordEncoder(passwordEncoder)
//                .username(username)
//                .password(password)
//                .roles("USER","ADMIN")
//                .build();
//        return userDetails;
//    }
//
//    //password 암호화
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    //All URLs are protected
//    // A login form is shown for unauthorized requests
//    // CSRF disable
//    // Frames
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        // csrf: 로컬에서 확인을 위해 csrf 비활성화
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // / , /login은 로그인 없이 접근 가능하도록 세팅.
//        http.authorizeHttpRequests(
//                auth -> auth
//                        .requestMatchers("/login","/").permitAll()
//                        .anyRequest().authenticated());
//
//        http.formLogin(Customizer.withDefaults());
//
//        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
//        return http.build();
    //}
}
