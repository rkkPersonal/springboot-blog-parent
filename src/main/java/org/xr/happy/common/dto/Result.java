package org.xr.happy.common.dto;


import lombok.Data;

@Data
public class Result {

    private Integer code;

    private String msg;

    private Object data;

    public Result() {

    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result ok() {

        return new Result(0, "success", "成功");

    }

    public static Result success(Object data) {
        return new Result(0, "success", data);

    }

    public static Result error(String errorMsg) {

        return new Result(1001, errorMsg, "失败");

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
