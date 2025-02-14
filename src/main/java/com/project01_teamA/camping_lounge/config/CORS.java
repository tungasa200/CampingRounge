//package com.project01_teamA.camping_lounge.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class CORS {
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        //1.setAllowedOrigins - 도메인 허용
//        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//
//        //2.setAllowedMethods - 허용 http
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","PATCH", "DELETE","PUT"));
//
//        //3.setAllowedHeaders - 허용 Headers
//        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
//
//        //세션, 인증 번호 허용 (임시)
//        configuration.setAllowCredentials(true);
//
//        //4.UIL 기본설정
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        //5.return
//        return source;
//    }
//}
