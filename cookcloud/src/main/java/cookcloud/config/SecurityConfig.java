package cookcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import cookcloud.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    // 🔥 UserDetailsService를 Bean으로 등록
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // 🔥 AuthenticationManager 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // 🔥 CSRF 비활성화 (테스트용)
	        .authorizeHttpRequests()
	        .requestMatchers("/signup", "/register").permitAll()
	        .anyRequest().permitAll()
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/mypage", true)
	        .permitAll()
	        .and()
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/")
	        .invalidateHttpSession(true)  // 세션 무효화
	        .deleteCookies("JSESSIONID")  // 쿠키 삭제 (JSESSIONID)
	        .permitAll()
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 필요 시 생성
	        .invalidSessionUrl("/") // 세션이 만료되면 리다이렉트할 URL
	        .maximumSessions(1) // 세션 수 1개로 제한하지 않음
	        .expiredUrl("/logout"); // 세션 만료시 이동 URL

        return http.build();
    }


    // 🔥 BCrypt 암호화 Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
