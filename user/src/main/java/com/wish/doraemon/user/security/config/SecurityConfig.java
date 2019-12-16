package com.wish.doraemon.user.security.config;

import com.wish.doraemon.user.config.RestApiConfig;
import com.wish.doraemon.user.dao.model.RoleEnum;
import com.wish.doraemon.user.security.exception.JwtAccessDeniedHandler;
import com.wish.doraemon.user.security.exception.JwtAuthenticationEntryPoint;
import com.wish.doraemon.user.security.filter.JwtAuthenticationFilter;
import com.wish.doraemon.user.security.filter.JwtAuthorizationFilter;
import com.wish.doraemon.user.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public SecurityConfig(@Lazy UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService() {
        return userDetailsServiceImpl;
    }

    /**
     * 配置UserDetail服务
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(bCryptPasswordEncoder());
//            .and()
//            // 内置默认的管理员账号
//            .inMemoryAuthentication()
//            .passwordEncoder(bCryptPasswordEncoder())
//            .withUser("admin")
//            .password(bCryptPasswordEncoder().encode("123456"))
//            .roles(RoleEnum.ADMIN.value());
    }

    /**
     * 配置安全策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            // 禁用 CSRF，防止被跨域攻击
            .csrf().disable()
            // 开启请求的权限校验
            .authorizeRequests()
            // 登录接口不需要权限
            .antMatchers(HttpMethod.POST, RestApiConfig.AUTHORIZATION).permitAll()
            // 指定路径下的资源需要验证了的用户才能访问
            .antMatchers("/api/**").authenticated()
            // 需要管理员权限才能执行删除操作（TODO 需要区分用户和管理的删除权限）
            .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(RoleEnum.ADMIN.value())
            // 其他请求放行
            .anyRequest().permitAll()
            .and()
            //添加自定义Filter
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager()))
            // 不需要session（不创建会话）
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 授权异常处理
            .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            .accessDeniedHandler(new JwtAccessDeniedHandler());
    }

}
