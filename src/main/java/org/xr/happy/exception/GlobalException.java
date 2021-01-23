package org.xr.happy.exception;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.xr.happy.common.dto.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=UTF-8");
            if (e instanceof NotFoundException) {
                logger.error("映射不存在:{}", e.toString());
                responseErrorMessage(writer, 404, "You are Lose");
                return;
            }
            logger.error("系统异常:{}", e.toString());
            responseErrorMessage(writer, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        } catch (IOException ioException) {
            logger.error("IO Exception  :{}", e.toString());
        } finally {
            writer.close();
        }

    }

    /**
     * return error message
     *
     * @param writer
     * @param code
     * @param msg
     */
    private void responseErrorMessage(PrintWriter writer, int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        String string = JSON.toJSONString(result);
        writer.println(string);
        writer.flush();
    }
}
