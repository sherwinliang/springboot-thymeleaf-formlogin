package com.sherwin.thymeleaf.config;

import com.sherwin.thymeleaf.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    PasswordEncoder myPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService myUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorze ->
                        authorze.antMatchers("/login")
                                .permitAll() //login page should be always be passed.
                                .anyRequest()
                                .authenticated() //Any authenticated requests can be passed.
                ).formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .loginProcessingUrl("/")//Point to the action of the login page
                                .failureForwardUrl("/login?error")
                );

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService()).passwordEncoder(myPasswordEncoder());
    }
    /* @description: static resources and htmls should not be intercepted.
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:30
     * @param:
     * @return:
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/user/**","/css/**");
    }
}
