package com.hu.health.community.config;

import com.hu.health.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ControllerAdvice 配合 @ExceptionHandler 实现全局异常处理
 * aop拦截规则，我帮你把他们拦下来，具体你想做更细致的拦截筛选和拦截之后的处理，
 * 你自己通过@ExceptionHandler、@InitBinder 或 @ModelAttribute这三个注解以及被其注解的方法来自定义。
 */
@ControllerAdvice(basePackages = "com.hu.health.community.controller")
@Slf4j
public class ControllerAdviceConfig {
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public R duplicateKey(DuplicateKeyException e) {
        e.printStackTrace();
        return R.error(200, "重复操作：" + e.getRootCause().getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R defaultException(Exception e) {
        e.printStackTrace();
        return R.error(e.getMessage());
    }
}
