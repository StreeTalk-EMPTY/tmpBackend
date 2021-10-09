package team.streetalk.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {


    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // 토큰 유효 기간
    public static final long JWT_TOKEN_VALIDITY = 30 * 60 * 60 * 24 * 1000L; //한달


    // 토큰에서 회원 정보 추출
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token).getBody();// JWT payload 에 저장되는 정보단위
        return claimsResolver.apply(claims);
    }

    public String getPhoneNumFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }


    //JWT 토큰 생성
    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("build token");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // 정보 저장
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 발행 시간 정보
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // set Expire Time
                .signWith(SignatureAlgorithm.HS512, secret)// 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    public Boolean validateToken(String token, CustomUserDetails customUserDetails) {
        final String PhoneNum = getPhoneNumFromToken(token);
        return (PhoneNum.equals(customUserDetails.getUsername())) && !isTokenExpired(token);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            throw new NullPointerException("Unable to get JWT");
        }


    }
}