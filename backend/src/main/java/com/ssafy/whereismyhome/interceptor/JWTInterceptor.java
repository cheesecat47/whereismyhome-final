package com.ssafy.whereismyhome.interceptor;

import com.ssafy.whereismyhome.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JWTInterceptor.class);

    private final String HEADER_AUTH = "Authorization";

    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader(HEADER_AUTH);

        if (token != null && jwtUtil.checkToken(token)) {
            logger.info("토큰 사용 가능: {}", token);
            return true;
        } else {
            logger.info("토큰 사용 불가능: {}", token);
            throw new RuntimeException("계정 권한이 유효하지 않습니다.");
        }
    }
}
