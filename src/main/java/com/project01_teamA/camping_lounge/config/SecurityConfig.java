package com.project01_teamA.camping_lounge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.cors().and()  // ✅ CORS 허용
                .csrf().disable()  // ✅ CSRF 비활성화 (테스트 시)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // ✅ 모든 요청 허용 (배포 시 수정 필요)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}