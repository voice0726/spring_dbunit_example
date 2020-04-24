package jp.voice0726.spring_junit_example.config;

import jp.voice0726.spring_junit_example.service.StudentService;
import jp.voice0726.spring_junit_example.service.impl.AdminUserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/img/**", "/css/**", "/js/**", "/webjars/**", "favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginProcessingUrl("/admin/login")
                .loginPage("/admin/login")
                .failureUrl("/admin/login?error")
                .defaultSuccessUrl("/admin/")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout**"))
                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .logoutSuccessUrl("/admin/login");
    }

    @Configuration
    @AllArgsConstructor
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        AdminUserServiceImpl adminUserService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(adminUserService)
                    .passwordEncoder(new BCryptPasswordEncoder());

        }
    }
}
