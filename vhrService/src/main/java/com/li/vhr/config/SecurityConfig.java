package com.li.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.vhr.api.CommonResult;
import com.li.vhr.model.Hr;
import com.li.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/8/6
 */
@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    HrService hrService;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 指定数据库中的Service
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().
//                authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customAccessDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        Hr principal = (Hr) authentication.getPrincipal();
                        principal.setPassword(null);
                        String json = new ObjectMapper().writeValueAsString(CommonResult.success(principal,"登录成功"));
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter writer = resp.getWriter();
                CommonResult r = CommonResult.failed("未知错误,登录失败");

                // 账户被锁定
                if (e instanceof LockedException) {
                    r = CommonResult.failed("账户锁定,登录失败");
                } else if (e instanceof CredentialsExpiredException) {
                    r = CommonResult.failed("密码过期,录失败");
                } else if (e instanceof AccountExpiredException) {
                    r = CommonResult.failed("账号过期,登录失败");
                } else if (e instanceof DisabledException) {
                    r = CommonResult.failed("账户禁用,登录失败");
                } else if (e instanceof BadCredentialsException) {
                    r = CommonResult.failed("用户名或者密码输入错误,登录失败");
                }

                writer.write(new ObjectMapper().writeValueAsString(r));
                writer.flush();
                writer.close();
            }
        })

                .permitAll().and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        writer.write(new ObjectMapper().writeValueAsString(CommonResult.success(null,"注销成功")));
                    }
                })
                .permitAll().and()
                .csrf().disable().
                // 没有认证,可以在这里处理结果不要重定向
                        exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            PrintWriter writer = response.getWriter();
            CommonResult r = CommonResult.failed("未知错误,登录失败");
            if (e instanceof InsufficientAuthenticationException) {
                r = CommonResult.failed("请求失败,请联系管理员");
            }

            writer.write(new ObjectMapper().writeValueAsString(r));
            writer.flush();
            writer.close();

        });
    }
}
