package com.yuunik.baseserive.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        /**
         * 做 Feign 的 Token 令牌中继
         */
        /*RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        System.out.println("______________________" + RequestContextHolder.currentRequestAttributes());
        if (requestAttributes == null) {
            // 记录日志
            System.err.println("Request attributes is null, this might indicate the current thread is not bound to a request.");
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 获取请求头信息
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            requestTemplate.header("Authorization", authorization);
        }
        System.out.println("requestTemplate -------->  " + requestTemplate);*/
    }
}
