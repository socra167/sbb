package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * com.mysite.sbb 패키지에 스프링 시큐리티의 설정을 담당
 * 내부적으로 SecurityFilterChain 클래스가 동작해 모든 요청 URL에 이 클래스가 필터로 적용되어 URL 별로 특별한 설정을 할 수 있게 된다.
 */
@Configuration // 스프링의 환경 설정 파일
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받게 함 (스프링 시큐리티 활성화)
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// "/**" 인증되지 않은 모든 페이지의 요청을 허락한다, csrf 처리 시 "/h2-console/**"는 예외로 처리한다
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.csrf((csrf) -> csrf
				.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
			.headers((headers) -> headers
				.addHeaderWriter(new XFrameOptionsHeaderWriter(
					XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.formLogin(formLogin -> formLogin // 스프링 시큐리티의 로그인 설정
				.loginPage("/user/login") // 로그인 페이지 URL
			.defaultSuccessUrl("/")); // 성공 시 이동할 페이지
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
