package com.project01_teamA.camping_lounge.security;

//import jhcode.blog.security.jwt.JwtAuthenticationEntryPoint;
//import jhcode.blog.security.jwt.JwtAuthenticationFilter;
import com.project01_teamA.camping_lounge.security.jwt.JwtAuthenticationEntryPoint;
import com.project01_teamA.camping_lounge.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //HttpSecurity 객체를 매개변수로 받아 보안 규칙을 설정한 후 SecurityFilterChain 객체를 반환
    //filterChain 메서드 실행 시점 : 1. 애플리케이션 시작 됐을 때 2. 클라이언트 요청이 들어올 때
    //jwtAuthenticationEntryPoint : 요청 URL이 설정된 접근 정책에 따라 인증이 필요한지 확인하고, 인증되지 않은 요청은 jwtAuthenticationEntryPoint에서 처리
    //참고 : 스프링 시큐리티는 클라이언트의 요청이 여러개의 필터를 거쳐 DispatcherServlet(Controller)으로 향하는 중간 필터에서 요청을 가로챈 후 검증(인증/인가)을 진행한다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpBasic -> httpBasic.disable()) //http basic 인증 방식 비활성화(JWT 인증을 사용하기 때문)
                .csrf(csrf -> csrf.disable()) //csrf 보호 비활성화(JWT는 토큰 기반 인증이기 때문에 세션 기반 보안이 필요 없기 때문에 CSRF 필요 없음)
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) //corsConfigurationSource 객체를 사용하여 어떤 도메인에서 요청을 허용할지 등의 CORS 정책을 설정

                .authorizeHttpRequests(authorize //인증 및 권한 설정
                        -> authorize
                        .requestMatchers("/member/*"
                                        ,"/admin/*"
                                ,"/*"
                                ,"/*/*"
                                ,"/*/*/*"
                                         ).permitAll() //해당 URL들은 인증 없이 접근 가능

                        //.anyRequest().permitAll()
                        
                        //아래는 역할(Role)을 가져야 하기 때문에 로그인을 하고 & 해당 role을 가진 사용자만 이용 가능
                        //.requestMatchers("/user/roleCheck").hasRole("USER")
                        //.requestMatchers("/user/roleCheck").hasRole("ADMIN")
                        //.requestMatchers("/user/**").hasRole("USER")
                        //.requestMatchers("/board/**").hasRole("USER")
                        //.requestMatchers("/board/{boardId}/comment/**").hasRole("USER")
                        //.requestMatchers("/board/{boardId}/file/**").hasRole("USER")
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 생성하거나 유지하지 않음(모든 요청에서 JWT 토큰을 확인하도록 강제함)
                .exceptionHandling(excep -> excep.authenticationEntryPoint(jwtAuthenticationEntryPoint)) //인증되지 않은 사용자가 보호된 리소스에 접근 시도하면, jwtAuthenticationEntryPoint에서 정의한 처리 방식으로 응답을 반환
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //jwtAuthenticationFilter(사용자 정의 JWT 인증 필터)를 Spring Security의 UsernamePasswordAuthenticationFilter 앞에 추가해서, JWT 토큰을 검증하는 필터를 기존 인증 필터보다 먼저 실행되도록 함
                .build();
    }
}
