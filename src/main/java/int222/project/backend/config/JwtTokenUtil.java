package int222.project.backend.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("#{${integrated.max-token-interval-hour}*60*60*1000}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token){return null;}

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){return null;}

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        return null;
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllCliamsFromToken(String token){ return null;}

    //check if the token is expired
    private Boolean isTokenExpired(String token){return null;}

    //generate token for user
    private String generateToken(UserDetails userDetails){ return null;}

    private String doGenerateToken(Map<String,Object> claims,String username){
            return Jwts.builder().setClaims(claims).setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                    .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
    //validate token
    public Boolean validateToken(String token,UserDetails userDetails){return null;}
}
