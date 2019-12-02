package ru.marina.tshop.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.marina.tshop.users.Role;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String ROLE_CLAIM_NAME = "roles";

    private final SecurityConfiguration configuration;
    private final Clock clock;

    @Autowired
    public JwtTokenProvider(final SecurityConfiguration configuration, final Clock clock) {
        this.configuration = configuration;
        this.clock = clock;
    }

    public String createToken(final String userId, final List<Role> roles) {
        final Claims claims = Jwts.claims()
                .setIssuer(configuration.getJwtIssuer())
                .setSubject(userId);
        claims.put(ROLE_CLAIM_NAME, roles);

        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime expires = now.plusSeconds(configuration.jwtExpiresInSeconds());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expires.toInstant()))
                .signWith(Keys.hmacShaKeyFor(configuration.getJwtSecretKey()), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(final HttpServletRequest req) {
        final String bearerToken = req.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) return null;

        final String token = bearerToken.substring(7);

        final JwtParser jwtParser = Jwts.parser()
                .setClock(new ClockAdapter(clock))
                .setSigningKey(configuration.getJwtSecretKey());

        final Claims body;
        try {
            body = jwtParser.parseClaimsJws(token).getBody();
        } catch (final Exception exception) {
            logger.error(exception.getMessage());
            return null;
        }

        final String subject = body.getSubject();
        final List<Role> roles = ((List<String>) body.get(ROLE_CLAIM_NAME, List.class))
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(subject, token, roles);
    }
}
