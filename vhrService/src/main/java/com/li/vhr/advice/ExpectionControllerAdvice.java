package com.li.vhr.advice;

import com.li.vhr.api.CommonResult;
import com.li.vhr.exception.OperationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author LiJinZe
 */
@RestControllerAdvice
public class ExpectionControllerAdvice {

    @ExceptionHandler({OperationException.class})
    public Object hanlder(Exception e) {
        OperationException exception = (OperationException) e;
        return CommonResult.failed(exception.getMsg());
    }

}
