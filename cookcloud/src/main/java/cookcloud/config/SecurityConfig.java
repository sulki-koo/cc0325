package cookcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cookcloud.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    // UserDetailsService Bean 등록
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // BCrypt 암호화 Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 🔥 AuthenticationManager 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build(); 
    }
    
    // 스프링 시큐리티 6 방식의 SecurityFilterChain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
                .csrf(csrf -> csrf.disable())  // 테스트 중에는 비활성화, 나중에 활성화 가능
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/signup", "/login", "/recipe/list", "/recipe/detail/**", "/checkDuplicate").permitAll()  // 🔥 누구나 접근 가능
                    .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()  // 🔥 회원가입 & 로그인 페이지 허용
                    .anyRequest().authenticated()  // 🔒 그 외 모든 요청 인증 필요
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/mypage", true)
                    .failureUrl("/login")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) // POST 방식 로그아웃
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .invalidSessionUrl("/login?session=invalid")
                )
                .build();
    }
}
