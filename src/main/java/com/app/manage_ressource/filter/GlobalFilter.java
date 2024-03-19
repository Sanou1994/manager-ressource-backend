package com.app.manage_ressource.filter;
import com.app.manage_ressource.entities.Utilisateur;
import com.app.manage_ressource.repositories.IDaoUser;
import com.app.manage_ressource.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import  javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * A filter to create transaction before and commit it once request completes
 * The current implemenatation is just for demo
 * @author hemant
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalFilter implements Filter {
    @Autowired
    IDaoUser daoUser;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws javax.servlet.ServletException {
        LOGGER.info("Initializing filter :{}", this);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String authorization=((HttpServletRequest) servletRequest).getHeader("authorization");
        LOGGER.info("Starting extracting for req :{}", httpRequest);
        if(authorization != null && authorization.length() !=0)
        {
            String token =authorization.substring(6).trim();
            String login =jwtTokenUtil.getUsernameFromToken(token);
            Utilisateur user=daoUser.findByEmail(login).get();
            httpServletResponse.setHeader("userID", user.getId().toString());
            httpServletResponse.setHeader("role", user.getRole().toString());

        }
         filterChain.doFilter(httpRequest, httpServletResponse);
        LOGGER.info("Filter ending for req :{}", httpServletResponse);

    }

    @Override
    public void destroy() {
        LOGGER.warn("Destructing filter :{}", this);
    }
}