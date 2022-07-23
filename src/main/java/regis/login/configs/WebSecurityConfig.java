package regis.login.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import regis.login.oauth.CustomOAuth2User;
import regis.login.oauth.CustomOAuth2UserService;
import regis.login.oauth.UserService;
import regis.login.services.CustomUserDetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private UserService userService;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Bean
    public UserDetailsService mongoUserDetails(){
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        UserDetailsService userDetailsService = mongoUserDetails();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/dashboard/**")
//                .hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler)
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
//                .accessDeniedPage("/accessDenied")
                .and()
                .oauth2Login().loginPage("/login")
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .successHandler((request, response, authentication) -> {
                    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                    userService.processOAuthPostLogin(oAuth2User.getFullName(),oAuth2User.getEmail(),oAuth2User.getPicture());
                    response.sendRedirect("/dashboard");
                })
                .userInfoEndpoint().userService(oAuth2UserService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web
                .ignoring()
                .antMatchers("/resources/**","/static/**","/css/**","/js/**","/images/**");
    }
}
