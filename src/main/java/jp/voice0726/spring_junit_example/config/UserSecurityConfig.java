package jp.voice0726.spring_junit_example.config;

import jp.voice0726.spring_junit_example.service.StudentService;
import jp.voice0726.spring_junit_example.service.impl.AdminUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Configuration
@EnableWebSecurity
@Order(200)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/img/**", "/css/**", "/js/**", "/webjars/**", "favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .logoutSuccessUrl("/login");
    }

    @Configuration
    @AllArgsConstructor
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        StudentService studentService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(studentService)
                    .passwordEncoder(new BCryptPasswordEncoder());

        }
    }
}
