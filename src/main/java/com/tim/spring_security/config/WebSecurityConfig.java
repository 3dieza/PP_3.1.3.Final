package com.tim.spring_security.config;

import com.tim.spring_security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userServiceImpl;
    private final SuccessUserHandler successUserHandler;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserServiceImpl userServiceImpl, SuccessUserHandler successUserHandler, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.successUserHandler = successUserHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/logout", "/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
    }

}