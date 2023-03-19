package com.kfm.config;

import com.kfm.exception.TokenAuthExpiredException;
import com.kfm.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 */
@RestControllerAdvice
@Slf4j
public class ExceptionConfig {
    /**
     * 参数为实体类
     * @param e
     * @return
     */
    @ExceptionHandler(TokenAuthExpiredException.class)
    public ResultEntity tokenAuthExpiredExceptionHandler(TokenAuthExpiredException e){
        log.error("无效的令牌",e);
        return new ResultEntity(400,"无效的令牌",null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object handleValidException(MethodArgumentNotValidException e) {
//         从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
//         然后提取错误提示信息进行返回
        return new ResultEntity<>(400,"参数验证失败",objectError.getDefaultMessage());
    }

    /**
     * 参数为单个参数或多个参数
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e) {
        // 从异常对象中拿到ObjectError对象
        return new ResultEntity<>(400,"参数验证失败",e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()).get(0));
    }
}
