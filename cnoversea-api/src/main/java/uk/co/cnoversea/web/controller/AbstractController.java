package uk.co.cnoversea.web.controller;

import uk.co.cnoversea.web.vo.ResponseVO;

import java.util.HashMap;
import java.util.Map;

public class AbstractController {

    public static ResponseVO<Map<String, Object>> genResponse() {
        return genResponse(ResponseVO.CODE_OK);
    }

    public static ResponseVO<Map<String, Object>> genResponse(int code) {
        return genResponse(code, "", new HashMap<String, Object>());
    }

    public static <T> ResponseVO<T> genResponse(T data) {
        return genResponse(ResponseVO.CODE_OK, "ok", data);
    }

    public static ResponseVO<Map<String, Object>> genResponse(int code, String msg) {
        return genResponse(code, msg, new HashMap<String, Object>());
    }

    public static <T> ResponseVO<T> genResponse(int code, T data) {
        return genResponse(code, "", data);
    }

    public static <T> ResponseVO<T> genResponse(int code, String msg, T data) {
        ResponseVO rvo = new ResponseVO();
        return genResponse(code, msg, data, rvo);
    }

    private static <T> ResponseVO<T> genResponse(int code, String msg, T data, ResponseVO<T> vo) {
        vo.setCode(code);
        vo.setMsg(msg);
        vo.setData(data);
        return vo;
    }
}
