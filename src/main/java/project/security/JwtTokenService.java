package project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import project.dto.UserAuthenticatedDTO;
import project.errors.UnauthorizedException;
import project.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenService implements IJwtTokenService{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Override
    public UserAuthenticatedDTO generateToken(User authentication) {
        return UserAuthenticatedDTO.builder()
                .token(Jwts.builder()
                    .setSubject(authentication.getDocumentNumber())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact())
                .expirationTime(this.jwtExpiration)
                .name(authentication.getName())
                .build();
    }

    @Override
    public String getDocumentNumberFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public void validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException ex) {
            throw new UnauthorizedException("Token Não Authorizado");
        }
    }

}
