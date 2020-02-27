package uk.co.cnoversea.web.vo;

public class ResponseVO<T> extends WebVO {
    /*
     * 0 正常
     * 1 錯誤
     * */
    public static final int CODE_OK = 0;
    public static final int CODE_ERR = 1;
    public static final int CODE_ERR_USER_NOT_EXIST = 2;//用户不存在
    public static final int CODE_ERR_USER_REPEAT = 3;//重复注册

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
