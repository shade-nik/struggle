package local.halflight.learning.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import local.halflight.learning.webservice.authentication.StruggleUserDetailsAuthenticationProvider;
import local.halflight.learning.webservice.service.StruggleUserService;

@Configuration
@EnableWebSecurity
//@Profile("dev")
public class DevWebApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private StruggleUserService struggleUserService;

	@Autowired
	@Qualifier("authenticationProvider")
	private StruggleUserDetailsAuthenticationProvider authenticationProvider;
	
	// TODO add repository implement remember me feature	
//	@Autowired
//  PersistentTokenRepository tokenRepository;	
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/webjars/**");
		web.ignoring().antMatchers("/usermgmnt.html");
	}
	
	 @Autowired
	 protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		 auth.authenticationProvider(daoAuthenticationProvider());
		// @formatter:off
		 auth.inMemoryAuthentication()
	  		.withUser("user")
	  		.password("user")
	  		.roles("USER").and()
	  		.withUser("admin")
	  		.password("admin")
	  		.roles("USER", "ADMIN");
		// @formatter:on
	  }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
//Properties->Java Code Style -> Formatter -> configure -> on/off tag
		http.csrf().disable();
		http
			.formLogin()
				.defaultSuccessUrl("/index.html")
				.loginPage("/login.html")
				.failureUrl("/login.html?error")
				.permitAll()
			.and()
			.logout()
				.logoutSuccessUrl("/login.html?logout")
				.logoutUrl("/logout.html")
				.permitAll()
			.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/rest/api/users/**")
				.hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/rest/api/users/**")
				.hasRole("ADMIN")
				.antMatchers("/rest/api/users/**")
				.hasRole("USER")
				.anyRequest().authenticated();
//			.and()
//				.rememberMe()
//				.rememberMeParameter("rememberMe")
//				.tokenRepository(tokenRepository)
	// @formatter:on
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() 
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(struggleUserService);
//		provider.setPasswordEncoder(encoder());
		return provider;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
