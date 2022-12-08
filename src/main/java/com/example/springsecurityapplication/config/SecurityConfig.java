package com.example.springsecurityapplication.config;

import com.example.springsecurityapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

@EnableWebSecurity
        //иной способ
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfiguration {
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final AuthenticationProvider authenticationProvider;
//
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
    private  final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // Конфигурация Spring Security
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        // указываем на какой url адрес фильтр Spring Security будет отправлять не аутентифицированного  пользователь при заходе на защищенную страницу

        //ранее отключали защиту от злоумышленников которые хотели подделать форму ввода данны. csrf - Cross-Site Request Forgery - изначально как атака на сайт. Защиту против нее включи спринг. Пи помощи привязки отправки данных через особый токен-пароль. Например нам нужно оплатить. Мы нажимаем на сайте оплатить. Злоумышленник мог на кнопку навесить свою ссыль. Однако данный токен в таком случае просто не примет банк, так как со стороннего сайта он будет иным
        //токен перехватить невозможно так как он привязан к конкретному браузеру пользователя
        //httpSecurity.csrf().disable()
        httpSecurity
                .authorizeRequests()// указываем что все страницы будут защищены процессом аутентификации

                .antMatchers("/admin")
                .hasRole("ADMIN")// указываем что страница /admin доступна пользователю с ролью ADMIN

                .antMatchers("/seller")
                .hasRole("SELLER")

                .antMatchers("/authentication/login","/authentication/registration", "/error","/product","/img/**","/product/info/{id}")
                .permitAll()// Указыаем что данные страницы доступна все пользователям

                .anyRequest()
                .hasAnyRole("USER", "ADMIN", "SELLER") // Указываем что все остальные страницы доступны пользователям с ролью user и admin

                .and()// Указываем что для всех остальных страниц необходимо вызывать метод authenticated(), который открывает форму аутентификации

                .formLogin()
                .loginPage("/authentication/login")

                .loginProcessingUrl("/process_login") // указываем на какой url будут отправляться данные с формы аутентификации

                .defaultSuccessUrl("/index", true) // Указываем на какой url необходимо направить пользователя после успешной аутентификации

                .failureUrl("/authentication/login?error") // Указываем куда необходимо перейти при неверный аутентификации
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/authentication/login")
        ;
    }



    // Данный метод позволяет настроить аутентификацию
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
