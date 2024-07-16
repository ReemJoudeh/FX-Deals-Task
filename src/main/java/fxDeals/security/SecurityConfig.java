//package fxDeals.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable().authorizeHttpRequests()
//                .requestMatchers("/").permitAll()
//                .requestMatchers("/api/deals/**").permitAll() // Allow access to /api/deals
//                .anyRequest().authenticated(); // Secure other endpoints
//        return http.build();
//    }
//
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable().authorizeHttpRequests()
////                .requestMatchers("/api/deals/**").permitAll() // Allow access to /api/deals
////                .anyRequest().authenticated(); // Secure other endpoints
////    }
//}
