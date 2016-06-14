package local.halflight.learning.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.DelegatingFilterProxy;

import local.halflight.learning.webservice.service.StruggleUserService;

@Configuration
@EnableWebSecurity
public class WebApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private StruggleUserService struggleUserService;

// TODO add repository implement remember me feature	
//	@Autowired
//    PersistentTokenRepository tokenRepository;	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	 @Autowired
	 protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//TODO change to use UserDetailService
		 auth.inMemoryAuthentication()
	  		.withUser("user")
	  		.password("user")
	  		.roles("USER").and()
	  		.withUser("admin")
	  		.password("admin")
	  		.roles("USER", "ADMIN");
	  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/rest/api/simple/task/**")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/rest/api/simple/task/**")
				.hasRole("ADMIN")
				.antMatchers("/rest/api/simple/task/**")
				.hasRole("USER")
				.anyRequest().authenticated()
				
			.and()
				.httpBasic();
//		    	.formLogin();
		
			http.csrf().disable();
	// @formatter:on
	}
	
	
}
