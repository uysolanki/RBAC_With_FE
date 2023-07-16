package com.gl.RoleBasedAuthorizationCode.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gl.RoleBasedAuthorizationCode.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebConfig  {

	public DaoAuthenticationProvider myAuthProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(myUs());
		daoAuthenticationProvider.setPasswordEncoder(myPw());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public UserDetailsService myUs()
	{
		return(new UserDetailsServiceImpl());
		
	}
	
	@Bean
	public BCryptPasswordEncoder myPw()
	{
	return new 	BCryptPasswordEncoder();
	}
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .antMatchers("/","/books/save","/books/showFormForAdd","/books/403").hasAnyAuthority("USER","ADMIN")
        .antMatchers("/books/showFormForUpdate","/books/delete").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().loginProcessingUrl("/login").successForwardUrl("/books/list").permitAll()
        .and()
        .logout().logoutSuccessUrl("/login").permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/books/403")
        .and()
        .cors().and().csrf().disable();
		
		http.authenticationProvider(myAuthProvider());
		
        return http.build();
    }

}
