package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserPermissions.COURSE_WRITE;
import static com.example.demo.security.ApplicationUserPermissions.STUDENT_WRITE;
import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private  final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*").permitAll()
              // .antMatchers("/api/**").hasRole(STUDENT.name())
//               .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//               .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//               .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
//               .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .defaultSuccessUrl("/courses",true)
               .and()
               .rememberMe()
               .tokenValiditySeconds((int) TimeUnit.DAYS.toDays(21))
               .key("verysecurekey")
               .and()
               .logout()
               .logoutUrl("/logout")
               .clearAuthentication(true)
               .invalidateHttpSession(true)
               .deleteCookies("JSESSIONID","remember-me")
               .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
     UserDetails user1=User.builder()
                .username("user1")
                .password(passwordEncoder.encode("pass1"))
//             .authorities(STUDENT.getGrantedAuthorities())
                .roles(STUDENT.name())
                .build();

        UserDetails user2= User.builder()
                .username("user2")
                .password(passwordEncoder.encode("pass2"))
                .authorities(ADMIN.getGrantedAuthorities())
//                .roles(ADMIN.name())
                .build();

        UserDetails user3= User.builder()
                .username("user3")
                .password(passwordEncoder.encode("pass3"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities())

//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .build();

        return  new InMemoryUserDetailsManager(user1,user2,user3);
    }
}
