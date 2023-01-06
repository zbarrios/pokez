package com.pokez.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.pokez.security.dto.JwtDtoModel;
import com.pokez.security.models.MainUserModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Integer expiration;

  public String generateToken(Authentication authentication){
    MainUserModel mainUser = (MainUserModel) authentication.getPrincipal();
    return Jwts.builder()
        .signWith(getKey(secret))
        .setSubject(mainUser.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + expiration * 1000))
        .claim("roles", getRoles(mainUser))
        .compact();
  }

  public String getUserEmail(String token){
    return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token)
        .getBody().getSubject();
  }

  /**
   * Validate the JWT.
   */
  public Boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Malformed token");
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported token");
    } catch (ExpiredJwtException e) {
      logger.error("Expired token");
    } catch (IllegalArgumentException e) {
      logger.error("Empty token");
    } catch (SignatureException e) {
      logger.error("Token signing error");
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  private List<String> getRoles(MainUserModel mainUser) {
    return mainUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  public Key getKey(String secret){
    byte[] secretBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(secretBytes);
  }

  public String refreshToken(JwtDtoModel jwtDto) throws ParseException {
    try {
      Jwts.parserBuilder().setSigningKey(getKey(secret))
          .build().parseClaimsJws(jwtDto.getToken());
    } catch (ExpiredJwtException e){
      logger.error("Expired token");
      JWT jwt = JWTParser.parse(jwtDto.getToken());
      JWTClaimsSet claims = jwt.getJWTClaimsSet();
      String userName = claims.getSubject();
      List<String> roles = (List<String>) claims.getClaim("roles");
      return Jwts.builder()
          .signWith(getKey(secret))
          .setSubject(userName)
          .setIssuedAt(new Date())
          .setExpiration(new Date(new Date().getTime() + expiration * 1000))
          .claim("roles", roles)
          .compact();
    }
    return "The token is not expired.";
  }

}
