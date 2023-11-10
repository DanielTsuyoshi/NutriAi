package br.com.fiap.nutriai.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import br.com.fiap.nutriai.models.Token;
import br.com.fiap.nutriai.models.UsuarioNutricao;
import br.com.fiap.nutriai.repository.UsuarioNutricaoRepository;

@Service
public class TokenService {

    @Autowired
    private UsuarioNutricaoRepository usuarioNutricaoRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UsuarioNutricao usuario) {
        Date now = new Date();
        Date expirationDate = Date.from(Instant.now().plus(expiration, ChronoUnit.MINUTES));

        String jwt = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer("NutriAI")
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return jwt;
    }

    public Authentication validate(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String email = claims.getSubject();

        UserDetails userDetails = usuarioNutricaoRepository.findByEmail(email)
                .orElseThrow(() -> new JWTVerificationException("Usuário não encontrado"));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
