package com.common.dipping.config;

import com.common.dipping.security.CustomAuthenticationFilter;
import com.common.dipping.jwt.JwtFilter;
import com.common.dipping.jwt.JwtProvider;
import com.common.dipping.security.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //JWT 제공 클래스
    private final JwtProvider jwtProvider;
    //인증 실패 또는 인증 헤더를 전달받지 못했을 때 핸들러
    private final AuthenticationEntryPoint authenticationEntryPoint;
    //인증 성공 핸들러
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    //인증 실패 핸들러
    private final AuthenticationFailureHandler authenticationFailureHandler;
    //인가 실패 핸들러
    private final AccessDeniedHandler accessDeniedHandler;

    //oauth2
    private final OAuth2UserServiceImpl OAuth2UserServiceImpl;



    //security 적용 무시
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //보안 기능 초기화 및 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) //인증 실패
                .accessDeniedHandler(accessDeniedHandler) //인가 실패
                .and().authorizeRequests()
                .antMatchers("/api/login", "/api/signUp","/api/findpw/*","/api/check/*").permitAll() //로그인 및 회원가입 요청은 허용
                .antMatchers("/api/signUp/info").hasRole("GUEST")
                .antMatchers("/**").permitAll()
                .antMatchers("/api/**").authenticated() //나머지 요청에 대해서는 인증을 요구
                //.antMatchers("/api/**").permitAll()
                .and()
                .formLogin().disable() //로그인 페이지 사용X
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .defaultSuccessUrl("http://localhost:3000") //oauth2 인증이 성공했을 때, 이동되는 url을 설정.
                .successHandler(authenticationSuccessHandler) //인증 프로세스에 따라 사용자 정의 로직을 실행.
                .userInfoEndpoint()
                .userService(OAuth2UserServiceImpl); //로그인이 성공하면 해당 유저의 정보를 들고 customOAuth2UserService에서 후처리
    }


    //사용자 요청 정보로 UserPasswordAuthenticationToken 발급하는 필터
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception{
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login"); // 필터 URL 설정
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler); // 인증 성공 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler); // 인증 실패 핸들러
        customAuthenticationFilter.afterPropertiesSet(); // BeanFactory에 의해 모든 property가 설정되고 난 뒤 실행
        return customAuthenticationFilter;
    }

    //JWT의 인증 및 권한을 확인하는 필터
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);
    }

}