package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsService userDetailsService;
    @Autowired
    public PasswordEncoder encoder;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authentication)
            throws Exception {
        authentication.userDetailsService(userDetailsService);
                /*.passwordEncoder(encoder);*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().antMatchers("/admin*//**")
                .access("hasRole('ADMIN')")
                .antMatchers("/user*//**").access("hasRole('USER')")
                .and().formLogin();*/
        http.authorizeRequests().antMatchers("/login").permitAll().and().formLogin();
    }
}
