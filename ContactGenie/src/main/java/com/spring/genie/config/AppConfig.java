package com.spring.genie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetails() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public DaoAuthenticationProvider getProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getDetails());
		provider.setPasswordEncoder(getEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain getFilter(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**")
				.hasRole("USER").requestMatchers("/**").permitAll().and().formLogin().loginPage("/signin")
				.loginProcessingUrl("/handleLogin").defaultSuccessUrl("/user/index")

				.and().csrf().disable();
		http.formLogin().defaultSuccessUrl("/user/index", true);
		return http.build();
	}

}
