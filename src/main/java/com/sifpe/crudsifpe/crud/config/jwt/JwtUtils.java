package com.sifpe.crudsifpe.crud.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretkey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Generar token de acceso
    public String generateAccesToken(String username, Collection<GrantedAuthority> roles) {
        return Jwts.builder()// Inicia la construcción del token JWT.
                .setSubject(username)// Establece el "sujeto" del token (generalmente el nombre de usuario).
                .claim("roles", roles)
                .setIssuedAt(new Date((System.currentTimeMillis())))// Establece la fecha y hora de emisión del token a
                                                                    // la hora actual.
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))// Establece la
                                                                                                     // fecha y hora de
                                                                                                     // vencimiento del
                                                                                                     // token.
                .signWith(this.getSignatureKey(), SignatureAlgorithm.HS256)// Firma el token con una clave secreta
                                                                           // usando el algoritmo HS256.
                .compact();// Finaliza la construcción del token y lo devuelve como una cadena compacta.
    }

    // validar el token de acceso
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token invalido error: ".concat(e.getMessage()));
            return false;
        }
    }

    // obtener el username
    public String getUsernameFromToken(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    // Obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // Obtener un solo claim
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener firma del token
    public Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
