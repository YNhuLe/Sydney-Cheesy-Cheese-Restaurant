package ca.sheridancollege.nhuyle.a4.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
/**Name: NhuY Le
 * Assignment: Sydney's Cheesey Cheese 2.2
Date: DEcember 15, 2022

Description: This application allow clients visit the Sydney's Cheesey Cheese 
website,view cheese and information about us.But only authenticated users with 
 correct name and password can log in and see the adding cheese to the inventory
calculate the value, and display cheese's information to the table.  */
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ca.sheridancollege.nhuyle.a4.services.UserDetailsServiceImpl;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServ;
	
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		 http.csrf().disable() .headers().frameOptions().disable();
		

		http.authorizeRequests()
				// manager can access to all the files in the inventory directory
				.antMatchers("/inventory/**").hasRole("MANAGER")
			
				// every one can access to "/images/**","/css/**","/**"
				.antMatchers("/images/**", "/css/**", "/**", "/register").permitAll()
				///view is accessible to customer
				.antMatchers("/view").hasRole("CUSTOMER")
				.antMatchers("/h2-console/**").permitAll()
				
				.anyRequest().authenticated()

				.and()
				.formLogin().loginPage("/login").permitAll()
				// all the files are visible to the authenticated user
				.defaultSuccessUrl("/#", true).permitAll()
				
				.and()
				.logout()
				// kill the session when user log out
				.invalidateHttpSession(true)
				//clear the authentication when user log out
				.clearAuthentication(true).
				logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				//after log out, go back to the home page
				.logoutSuccessUrl("/").permitAll();

	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsServ).passwordEncoder(passwordEncoder());
		
	}

}

