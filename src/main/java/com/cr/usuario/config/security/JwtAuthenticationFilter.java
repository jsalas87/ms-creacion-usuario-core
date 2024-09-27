package com.cr.usuario.config.security;

import com.cr.usuario.application.service.UserService;
import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.ErrorHandler;
import com.cr.usuario.domain.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.contains("/user/create") || path.contains("/_ms-creacion-usuario-core/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.getUsernameFromToken(token);
        }
        log.info("Se obtiene del token el usuario {}", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userService.findByEmail(username);

            if (jwtService.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null,null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(createErrorResponse(ErrorCode.INVALID_TOKEN.getReasonPhrase()));
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(createErrorResponse(ErrorCode.UNAUTHORIZED.getReasonPhrase()));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String createErrorResponse(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorHandler.ApiErrorResponse error = ErrorHandler.ApiErrorResponse.builder()
                .message(message)
                .status(401)
                .build();
        return objectMapper.writeValueAsString(error);
    }
}

