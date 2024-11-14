package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
				.defaultSuccessUrl("/main")
			.and()
				.logout()
				.permitAll()
			.and()
					.authorizeRequests((requests) -> requests
                    .antMatchers("/tradingCards/view**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/tradingCards/list**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/logout**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/login**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/tradingCards", "/tradingCards/").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/tradingCards/add").hasRole("ADMIN")
                    .antMatchers("/tradingCards/edit**").hasRole("ADMIN")
                    .antMatchers("/tradingCards/delete**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                );
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

	@Bean
    public AuthenticationProvider authenticationProvider() {

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService((UserDetailsService) service);

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner initializeUsers(final PasswordEncoder passwordEncoder) {
        return args -> {
            // This setup initializes users and roles; ensure UserService saves these appropriately
            service.deleteByUsername("user");
            service.deleteByUsername("admin");
            
            service.save(new User("user", passwordEncoder.encode("password"), "ROLE_USER"));
            service.save(new User("admin", passwordEncoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}



