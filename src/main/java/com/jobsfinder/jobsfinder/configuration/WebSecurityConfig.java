package com.jobsfinder.jobsfinder.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jobsfinder.jobsfinder.dao.UserDetailsServiceImpl;

 
@Configuration//classe config 
@EnableWebSecurity//activer partie securite fel projet
@EnableGlobalMethodSecurity(//tactivi les methodes mta3 el spring security
    prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {//teb3a jar spring security
    @Autowired//injection des dependances
    UserDetailsServiceImpl userDetailsService;//a7na 3malneha
 
    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;//definie fi spring security
 
    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }
 
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {//tgofiguri el authetification
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)//tgib les details mta3 el user
                .passwordEncoder(passwordEncoder());//tcrytpti el mdp
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {//authentification
        return super.authenticationManagerBean();
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {//traja3 mdp crypte
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {//
        http.cors().and().csrf().disable().
                authorizeRequests()//tactivi el api eli 3andek el 7a9 t7elhom
                .antMatchers("/api/auth/**").permitAll()//tnajem t7el les api ken eli yebdew bel api/auth sinon itha t7eb kolchy **
                .anyRequest().authenticated()//ay req bel forma atheka w ykoun authentifier
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()//gener les exceptions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}