package uk.co.cnoversea.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import uk.co.cnoversea.api.dao.model.User;
import uk.co.cnoversea.api.service.IUserService;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;

import java.util.Map;

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
            if (e instanceof DuplicateKeyException) {
                return genResponse(ResponseVO.CODE_ERR, "repeat uid : " + user.getUid(), userRet);
            } else {
                return genResponse(ResponseVO.CODE_ERR, "regist fail, " + e.getMessage() , userRet);
            }
        }
    }

    //登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<Map<String, Object>> login(@RequestBody RequestVO<User> param) {
        try{
            if (userService.login(param.getQuery())) {
                return genResponse();
            } else {
                return genResponse(ResponseVO.CODE_ERR, "login fail");
            }
        }catch(Exception e){
            logger.error("login fail", e);
            return genResponse(ResponseVO.CODE_ERR, "login fail, " + e.getMessage());
        }
    }

}
