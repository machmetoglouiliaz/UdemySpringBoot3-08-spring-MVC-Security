package com.mourat.udemy.springsecuritydemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {
/*
    @Bean
    public InMemoryUserDetailsManager manager(){
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }
    
 */

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery("SELECT user_id, pw, active FROM members WHERE user_id=?");
        manager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id=?");

        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                    configurer
                            .requestMatchers("/").hasRole("EMPLOYEE")
                            .requestMatchers("/leaders/**").hasRole("MANAGER")
                            .requestMatchers("/admins/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .formLogin(form ->
                        form.loginPage("/showLogin")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                );

        return http.build();
    }
}
