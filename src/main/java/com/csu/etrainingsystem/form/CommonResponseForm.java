package com.csu.etrainingsystem.form;

/**
 * 与前端交互
 * 封装数据
 */
public class CommonResponseForm {
    private Integer status;
    private String message;
    private Object data;

    public CommonResponseForm(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponseForm(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * success with data
     */
    public static CommonResponseForm of200(String message,Object data){
        return new CommonResponseForm(0,message,data);
    }

    /**
     * success without data
     * @return form
     */
    public static CommonResponseForm of204(String message){
        return new CommonResponseForm(0,message);
    }

    /**
     * 400 error
     * @return error form with message
     */
    public static CommonResponseForm of400(String message){
        return new CommonResponseForm(400,message);
    }

    /**
     * 401 error
     * without authority
     */
    public static CommonResponseForm of401(String message){
        return new CommonResponseForm(401,message);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
