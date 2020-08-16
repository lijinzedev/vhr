package com.li.vhr.advice;

import com.li.vhr.api.CommonResult;
import com.li.vhr.api.ResultWarrp;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author LiJinZe
 */
@RestControllerAdvice
public class ResponseControllerAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 如果是提示性消息
        if (o instanceof String) {
            return CommonResult.success(null, (String) o);
            // 如果是包裹的类型
        } else if (o instanceof ResultWarrp) {
            return CommonResult.success(((ResultWarrp) o).getData(), ((ResultWarrp) o).getMsg());
        } else if (o instanceof CommonResult) {
            return o;
        }
        return CommonResult.success(o);
    }

}
