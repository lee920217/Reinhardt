package uk.co.cnoversea.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import uk.co.cnoversea.api.dao.model.User;
import uk.co.cnoversea.api.exception.UserNotExistException;
import uk.co.cnoversea.api.service.IUserService;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    //注册
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<User> regist(@RequestBody RequestVO<User> param) {
        User user = param.getQuery();
        User userRet = null;
        try {
            userRet = userService.regist(user);
            return genResponse(userRet);
        } catch (Exception e) {
            logger.error("regist user fail", e);
            if (e instanceof DuplicateKeyException) {
                return genResponse(ResponseVO.CODE_ERR_USER_REPEAT, "repeat uid : " + user.getUid() + ", email : " + user.getEmail(), userRet);
            } else {
                return genResponse(ResponseVO.CODE_ERR, "regist fail, " + e.getMessage() , userRet);
            }
        }
    }

    //激活
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<User> active(@RequestBody RequestVO<User> param) {
        User user = param.getQuery();
        User userRet = null;
        try {
            userRet = userService.active(user);
            return genResponse(userRet);
        } catch (Exception e) {
            logger.error("active user fail", e);
            return genResponse(ResponseVO.CODE_ERR, "active fail, " + e.getMessage() , user);
        }
    }

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<User> login(@RequestBody RequestVO<User> param) {
        User user = param.getQuery();
        try{
            if (userService.login(user)) {
                return genResponse(user);
            } else {
                return genResponse(ResponseVO.CODE_ERR, "login fail", user);
            }
        }catch(Exception e){
            logger.error("login fail", e);
            if (e instanceof UserNotExistException) {
                return genResponse(ResponseVO.CODE_ERR_USER_NOT_EXIST, "repeat uid : " + user.getUid() + ", email : " + user.getEmail(), user);
            } else{
                return genResponse(ResponseVO.CODE_ERR, "login fail, " + e.getMessage(), user);
            }
        }
    }

}
