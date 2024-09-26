package com.yuunik.baseserive.interceptor;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.utilscommon.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 全局校验拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    // 前置拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许预检请求通过
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String url = request.getRequestURI();
        if (url.contains("/ucenterservice/member/loginUser") ||
                url.contains("/ucenterservice/member/registerUser") ||
                url.contains("/api/ucenter/wx/getQRCode/**") ||
                url.contains("/api/ucenter/wx/callback") ||
                url.contains("/error")
        ) {
            // 不需要做 token 校验
            return true;
        }
        // 获取 Authorization 信息
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // 抛出异常
            throw new YuunikException(20001, "请登录");
        }
        // 获取 token
        String token = authorization.substring(7);
        // 校验 token 的有效性
        if (!JwtUtil.checkToken(token)) {
            // 抛出异常
            throw new YuunikException(20001, "token 失效, 请重新登录");
        }
        // 校验通过
        return true;
    }
}
