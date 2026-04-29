package com.tiendasgo.catalog.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    // Soporta ambas claves: project.jwt.secret (tg-auth-core) o jwt.secret (posible variable de entorno)
    @Value("${project.jwt.secret:${jwt.secret:}}")
    private String jwtSecret;

    private SecretKey getSecretKey() {
        if (!StringUtils.hasText(jwtSecret)) {
            log.error("Falta la propiedad JWT secret. Configure 'project.jwt.secret' o 'jwt.secret' en application.yaml o como variable de entorno.");
            throw new IllegalStateException("JWT secret no configurado");
        }
        // Mostrar longitud (no la clave completa) para debugging
        log.debug("Usando jwt.secret de longitud: {}", jwtSecret.length());
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Claims validateTokenAndGetClaims(String token) {
        try {
            // Asegurarse de llamar a build() antes de parseClaimsJws
            return Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            log.warn("Error al validar JWT: {}", ex.getMessage());
            throw ex;
        }
    }

    public String getEmail(String token) {
        Claims claims = validateTokenAndGetClaims(token);
        return claims.getSubject();
    }

    public List<String> getRoles(String token) {
        Claims claims = validateTokenAndGetClaims(token);
        Object roles = claims.get("roles"); // Extrae la lista "limpia" (ej: ["ADMIN"])

        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }
}
