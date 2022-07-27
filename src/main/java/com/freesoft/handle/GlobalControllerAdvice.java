package com.freesoft.handle;

import com.freesoft.common.enums.ResultStatusEnums;
import com.freesoft.common.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description: </br>
 * date: 2020/6/30 17:01</br>
 *
 * @author Administrator</ br>
 * @since JDK 1.8
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //发生异常进行日志记录，写入数据库或者其他处理
        log.error(e.getLocalizedMessage());
        e.printStackTrace();//打印堆栈信息，方便定位
        //如果是入参校验异常
        StringBuilder stringBuilder = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e1 = (MethodArgumentNotValidException) e;
            BindingResult result = e1.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.stream().map(p -> (FieldError) p).map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage() + ",").forEach(stringBuilder::append);
            }
        } else {
            stringBuilder.append(e.getLocalizedMessage());
        }
        return ResponseResult.builder().code(ResultStatusEnums.FAIL.getCode()).message(ResultStatusEnums.FAIL.getMessage()).build();
    }
}
