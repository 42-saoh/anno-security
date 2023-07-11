package com.example.security.jwt;

import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityService securityService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(SecurityService securityService, JwtUtil jwtUtil) {
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/") || path.equals("/login") || path.equals("/oauth2")) {
            chain.doFilter(request, response);
            return ;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String naverId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            naverId = jwtUtil.extractNaverId(jwt);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
            return ;
        }
        if (path.equals("/sign")) {
            if (jwtUtil.validateToken(jwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }
            chain.doFilter(request, response);
            return ;
        }
        if (naverId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.securityService.loadUserByUsername(naverId);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return ;
            }
        }
        request.setAttribute("naverId" ,((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        chain.doFilter(request, response);
    }
}