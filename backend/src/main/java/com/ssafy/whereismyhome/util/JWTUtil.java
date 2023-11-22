package com.ssafy.whereismyhome.util;


import com.ssafy.whereismyhome.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    /**
     * Access Token 생성
     *
     * @param memberId 회원 아이디
     * @param email    회원 이메일
     * @return Access Token
     */
    public String createAccessToken(int memberId, String email) {
        return create(memberId, email, "access-token", accessTokenExpireTime);
    }

    /**
     * Refresh Token 생성. Access Token에 비해 유효기간을 길게 설정.
     *
     * @param memberId 회원 아이디
     * @param email    회원 이메일
     * @return Refresh Token
     */
    public String createRefreshToken(int memberId, String email) {
        return create(memberId, email, "refresh-token", refreshTokenExpireTime);
    }

    /**
     * 입력 받은 정보를 사용하여 토큰 발급
     *
     * @param memberId   회원 아이디
     * @param email      회원 이메일
     * @param subject    토큰 제목: 'access-token' 또는 'refresh-token'
     * @param expireTime 만료 시간
     * @return
     */
    private String create(int memberId, String email, String subject, long expireTime) {
        // Payload 설정: 토큰 발행일(IssuedAt), 유효기간(Expiration), 토큰 제목(Subject), 데이터(Claims) 등 정보 세팅.
        Claims claims = Jwts.claims()
                .setSubject(subject)        // 토큰 제목: 'access-token' 또는 'refresh-token'
                .setIssuedAt(new Date())    // 토큰 발행일
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));  // 토큰 만료일(유효기간)

        // payload data
        claims.put("memberId", memberId);
        claims.put("email", email);

        // 토큰 생성
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT") // 토큰 타입: JWT
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, this.generateKey()) // hash 알고리즘, secret을 활용한 암호화.
                .compact(); // 직렬화
        logger.debug("jwt: {}", jwt);

        return jwt;
    }

    /**
     * Signature 설정에 들어갈 key 생성
     *
     * @return UTF-8 encoded key
     */
    private byte[] generateKey() {
        return salt.getBytes(StandardCharsets.UTF_8);
    }

    private Jws<Claims> parseClaims(String token) {
        token = token.replaceAll("^Bearer ", "");
        logger.debug("token: {}", token);

        // JSON Web Signature(JWS): 서버에서 인증을 근거로 인증 정보를 서버의 privkey로 서명한 것을 토큰화한 것.
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.generateKey())  // 서명 검증을 위한 secret 세팅.
                .parseClaimsJws(token);             // token을 파싱하여 원본 JWS 얻기.
        // Claims는 Map의 구현체 형태
        logger.debug("claims: {}", claims);
        return claims;
    }

    /**
     * 전달 받은 토큰이 제대로 생성된 것인지 유효성 검사하고 문제가 있다면 예외 발생
     *
     * @param token 요청에 포함된 토큰
     * @return 유효성 T/F
     */
    public boolean checkToken(String token) {
        Jws<Claims> claims = parseClaims(token);
        logger.debug("claims: {}", claims);
        return claims != null;
    }

    /**
     * 토큰에서 회원 아이디 추출 후 반환
     *
     * @param token 요청에 포함된 토큰
     * @return memberId
     */
    public int getMemberId(String token) {
        Jws<Claims> claims = parseClaims(token);
        Map<String, Object> body = claims.getBody();
        return (int) body.get("memberId");
    }

    /**
     * 토큰에서 회원 이메일 추출 후 반환
     *
     * @param token 요청에 포함된 토큰
     * @return email
     */
    public String getMemberEmail(String token) {
        Jws<Claims> claims = parseClaims(token);
        Map<String, Object> body = claims.getBody();
        return (String) body.get("email");
    }
}
