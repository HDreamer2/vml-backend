package com.hku.vmlbackend.handler;


import com.hku.vmlbackend.common.result.Result;
import com.hku.vmlbackend.constant.MessageConstant;
import com.hku.vmlbackend.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL异常
     * @Param [ex]
     * @Return com.sky.result.Result
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String msg = ex.getMessage();
        if(msg.contains("Duplicate entry")){
            return Result.error(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
        else return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
