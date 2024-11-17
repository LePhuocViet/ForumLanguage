package com.ForumLanguage.Forum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.security.sasl.AuthenticationException;
import javax.sql.DataSource;

@Configuration
public class AccountSecurity {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username,password, true from account where username=?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select a.username, r.role from account a inner join role r on a.id = r.id_account where a.username=?"
        );
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/Post","/Setting").hasRole("USER")
                        .requestMatchers("**","/Home","/Authentication").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/Login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                        .failureUrl("/Login?error=true")
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error", "Username or Password is Wrong");
                            response.sendRedirect("/Login?error=true"); //
                        }))
                .logout(logout -> logout
                        .permitAll()
                );

        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
