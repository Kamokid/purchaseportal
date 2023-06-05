package com.cst8333.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	 public UserDetailsService userDetailsService() {
	        return new PurchaseUserDetailService();
	  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();		
	}
	 
	 public DaoAuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		 authProvider.setUserDetailsService(userDetailsService());
		 authProvider.setPasswordEncoder(passwordEncoder());
		 return authProvider;
	 }
	 	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	 
	@Override
	protected void configure(HttpSecurity http)throws Exception {
			http.authorizeRequests()
			.antMatchers("/users/**").hasAuthority("Admin")
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll();
	}
	
//	 @Bean
//	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	     
//	        http.authorizeRequests().antMatchers("/login").permitAll()
//	                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
//	                .hasAnyAuthority("Admin", "Editor", "Salesperson")
//	                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
//	                .anyRequest().authenticated()
//	                .and().formLogin()
//	                .loginPage("/login")
//	                    .usernameParameter("email")
//	                    .permitAll()
//	                .and()
//	                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//	                .and()
//	                .logout().permitAll();
//	 
//	        http.headers().frameOptions().sameOrigin();
//	 
//	        return http.build();
//	    }
	 
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		
//	}
//	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**","/js/**","/webjars/**","/style/**");
	}

}
