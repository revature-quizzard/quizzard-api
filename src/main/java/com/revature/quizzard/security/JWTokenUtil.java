package com.revature.quizzard.security;

import com.revature.quizzard.dtos.AuthenticatedDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTokenUtil {

    public static final long JWT_EXPIRATION = 5L * 60L * 60L;
    private static final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;

    private Key secretKey;

    @Value("${jwt.secret}")
    private String SECRET;

    public JWTokenUtil(){

    }



    @PostConstruct
    public void init(){
        this.secretKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET),sigAlg.getJcaName());
    }

    /*
    Make sure token String splits off "Bearer" to properly parse token.
    @JamesFallon
     */

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Key getSecretKey() {
        return this.secretKey;
    }

    private Claims getAllClaimsFromToken(String token) {
        System.out.println("Inside JWToken: " + token);
        token = token.split(" ")[1];
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    public int getIdFromToken(String token) {
        //token = token.split(" ")[1];
        return Integer.parseInt(getClaimFromToken(token, Claims::getId));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        token = token.split(" ")[1];
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    /**
     * Generates a token based on the fields provided by a User Data Transfer Object
     * @param authenticatedDTO The user in question to generate the token about
     * @return The JWT to return to the calling service
     */
    public String generateToken(AuthenticatedDTO authenticatedDTO) {
        return Jwts.builder()

                .setIssuer("Revature Quizzard")
                .setId("" + authenticatedDTO.getId())
                .setSubject(authenticatedDTO.getUsername())
                .claim("id","" + authenticatedDTO.getId())
                .claim("userName", authenticatedDTO.getUsername())
                .claim("role", authenticatedDTO.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000))
                .signWith(
                        sigAlg,
                        this.secretKey)
                .compact();
    }

    public Boolean validateToken(String token, int id) {
        final int tokenId = getIdFromToken(token);
        return ((tokenId == id) && !isTokenExpired(token));
    }

}
