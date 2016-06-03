package local.halflight.learning.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import local.halflight.learning.webservice.service.StruggleUserService;

@Configuration
@EnableWebSecurity
public class WebApiSecurityConfig extends WebSecurityConfigurerAdapter {
//This will enable global security 
	
//TODO use this service for user authentication
	@Autowired
	private StruggleUserService struggleUserService;
		 
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	 @Autowired
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  		auth.inMemoryAuthentication()
	  		.withUser("TestUser")
	  		.password("TestPassword")
	  		.roles("USER").and()
	  		.withUser("admin")
	  		.password("admin")
	  		.roles("USER", "ADMIN");
	  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/rest/api/simple/task/**")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/rest/api/simple/task/**")
				.hasRole("ADMIN")
				.antMatchers("/rest/api/simple/task/**")
				.hasRole("USER")
			.and()
		    	.formLogin();
	}
	
	
}
