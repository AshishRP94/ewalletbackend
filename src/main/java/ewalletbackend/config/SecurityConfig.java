package ewalletbackend.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.User;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	UserDetailsService userdetailsservice;
	
	@Autowired
	UserDao userdao;
	
	@Autowired
	JwtAuthEntryPoint jwt;
	
	
	@Autowired
	JwtAuthenticationFilter jwtfilter;

//	@Autowired
//	CustomOAuth2UserService  oauthuser;
	
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return   NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		PasswordEncoder passwordEncoder=passwordEncoder();
		
		auth
		      .userDetailsService(userdetailsservice)
		      .and()
		      .inMemoryAuthentication()
		      .withUser("admin")
              .password(passwordEncoder().encode("password"))
              .authorities("admin");
		      
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
//		unauthorizedHandler
        http
        .csrf()
        .disable()
        .cors()
        .disable()
        .authorizeRequests()
        .antMatchers("/generatetoken","/register")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwt)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
     
	}
	
	
	
	
	
}
