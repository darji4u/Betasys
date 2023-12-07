package com.noxsoft.betasys.Config;

import com.noxsoft.betasys.Model.UserCredentialModel;
import com.noxsoft.betasys.Model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRED_KEY = "743777217A25432A462D4A614E645267556B586E3272357538782F413F442847";

    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }


    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRED_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String generateToken(Map<String,Object> extractClaims, UserModel userModel){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userModel.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserModel userModel){
        return generateToken(new HashMap<>(),userModel);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }


}
