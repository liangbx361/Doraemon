package com.wish.doraemon.user.security.filter;

import com.wish.doraemon.user.security.jwt.JwtHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * JWT认证处理
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthorizationFilter.class.getName());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String authorization = request.getHeader(JwtHandler.TOKEN_HEADER);

        // 如果请求头中没有token信息则直接放行了
        if (authorization == null || !authorization.startsWith(JwtHandler.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // 如果请求头中有token，则进行解析，并且保存认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        String token = authorization.replace(JwtHandler.TOKEN_PREFIX, "");

        try {
            // 获取用户名
            String username = JwtHandler.getInstance().getUsernameByToken(token);
            if (StringUtils.isEmpty(username)) {
                return null;
            }

            // 获取用户角色
            List<SimpleGrantedAuthority> roles = JwtHandler.getInstance().getUserRolesByToken(token);

            return new UsernamePasswordAuthenticationToken(username, null, roles);
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception) {
            logger.warning("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }

        return null;
    }
}
