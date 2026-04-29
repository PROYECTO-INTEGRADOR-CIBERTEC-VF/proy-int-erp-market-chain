package com.tiendasgo.catalog.config.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtProvider.validateTokenAndGetClaims(token);
                String email = claims.getSubject();

                // Log completo de claims para debugging
                log.debug("JWT claims: {}", claims);

                List<String> roles = jwtProvider.getRoles(token);

                // Normalizar roles: quitar prefijo "ROLE_" si existe y convertir a mayúsculas
                var authorities = roles.stream()
                        .map(role -> {
                            String r = role == null ? "" : role.trim().toUpperCase();
                            if (r.startsWith("ROLE_")) {
                                r = r.substring(5);
                            }
                            return new SimpleGrantedAuthority("ROLE_" + r);
                        })
                        .collect(Collectors.toList());

                log.debug("Roles extraídos: {} -> Authorities: {}", roles, authorities);

                var authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                // Loguear y responder 401 para facilitar debugging
                log.warn("Token inválido o expirado: {}", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
                return; // No continuar la cadena de filtros
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
