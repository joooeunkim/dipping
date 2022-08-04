package com.common.dipping.config.interceptor;

import com.common.dipping.constants.AuthConstants;
import com.common.dipping.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {

        log.info("!!!!!!!!!!! JWT Token Interceptor");

        final String header = request.getHeader(AuthConstants.AUTH_HEADER);

        if (header != null) {
            final String token = TokenUtils.getTokenFromHeader(header);
            if (TokenUtils.isValidToken(token)) {
                return true;
            }
        }
        return false;

    }

}