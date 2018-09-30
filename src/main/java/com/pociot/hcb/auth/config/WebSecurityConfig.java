package com.pociot.hcb.auth.config;

import com.pociot.hcb.auth.service.security.MongoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final MongoUserDetailsService userDetailsService;
  private final TokenEndpointAuthFilter tokenEndpointAuthFilter;

  public WebSecurityConfig(
      MongoUserDetailsService userDetailsService,
      TokenEndpointAuthFilter tokenEndpointAuthFilter) {
    this.userDetailsService = userDetailsService;
    this.tokenEndpointAuthFilter = tokenEndpointAuthFilter;
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(tokenEndpointAuthFilter, ChannelProcessingFilter.class)
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .csrf().disable();
  }
}
