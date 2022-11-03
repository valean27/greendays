package com.greendays.greendays.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomWebSecurityConfigurerAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("bfilip")
                        .password(passwordEncoder.encode("tudor"))
                        .roles("student")
                        .build(),
                User.builder()
                        .username("stefan")
                        .password(passwordEncoder.encode("petru"))
                        .roles("ADMIN")
                        .build(),
                User.builder()
                        .username("Jorge")
                        .password(passwordEncoder.encode("Rodrigues!12"))
                        .roles("Admin")
                        .build(),
                User.builder()
                        .username("GreendaysAdmin")
                        .password(passwordEncoder.encode("AdminGreendays!12"))
                        .roles("Admin")
                        .build()
        );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web
                .ignoring()
                .antMatchers("/h2-console/**").and().ignoring().antMatchers("/garbage/**");
    }


}