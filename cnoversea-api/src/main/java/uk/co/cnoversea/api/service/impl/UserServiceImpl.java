package uk.co.cnoversea.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.mapper.UserMapper;
import uk.co.cnoversea.api.dao.model.User;
import uk.co.cnoversea.api.dao.model.UserExample;
import uk.co.cnoversea.api.exception.UserNotExistException;
import uk.co.cnoversea.api.service.IUserService;
import uk.co.cnoversea.common.JavaMail;
import uk.co.cnoversea.common.security.MD5Util;
import uk.co.cnoversea.common.string.Simple;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements InitializingBean,IUserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    private JavaMail mail;

    @Value("${user.regist.mail.validate}")
    private String registMailValidate;
    private static final String NEED_MAIL_VALIDATE = "1";

    @Override
    public void afterPropertiesSet() throws Exception {
        mail = new JavaMail();
    }

    @Override
    public User regist(User user) throws Exception {
        if (Simple.StringNull(user.getUid())) {
            throw new Exception("invalid uid : " + user.getUid());
        }

        user.setCreateTime(null);
        user.setModifyTime(null);

        user.setUuid(Simple.genUUID32());
        String newPass = user.getPass();
        newPass = MD5Util.queryMD5(newPass);
        user.setPass(newPass);
        if(NEED_MAIL_VALIDATE.equals(registMailValidate)){
            user.setStatus(null);//数据库表中此字段默认值是注册状态
        }else{
            user.setStatus(User.STATUS_ACTIVE);
        }

        if (userMapper.insertSelective(user) == 1) {
            try {
                if(NEED_MAIL_VALIDATE.equals(registMailValidate)){
                    mail.send(mail.getTitle(), mail.getContent() + user.getUuid(), Arrays.asList(user.getEmail()), null);
                }
            }catch(Exception e){
                logger.error("send mail fail", e);
                return null;
            }
            user.setPass(null);
            return user;
        }
        return null;
    }

    @Override
    public User active(User user) throws Exception {
        if (Simple.StringNull(user.getUuid()) || user.getUuid().length() != 32) {
            throw new Exception("invalid uuid : " + user.getUuid());
        }

        User record = userMapper.selectByPrimaryKey(user.getUuid());
        if(record == null){
            throw new Exception("UUID not exists(" + user.getUuid() + ")");
        }
        if(record.getStatus().intValue() != User.STATUS_REG.intValue()){
            throw new Exception("invalid status(" + user.getUuid() + ")");
        }

        User newUser = new User();
        newUser.setUuid(user.getUuid());
        newUser.setStatus(User.STATUS_ACTIVE);
        if(userMapper.updateByPrimaryKeySelective(newUser) == 1){
            newUser.setPass(null);
            return newUser;
        }

        return null;
    }

    @Override
    public boolean login(User user) throws Exception {
        UserExample e = null;
        List<User> list = null;

        //uid和email都可以登录
        if(Simple.StringNotNull(user.getUid())){
            e = new UserExample();
            e.createCriteria().andUidEqualTo(user.getUid());
            list = userMapper.selectByExample(e);
        }else if(Simple.StringNotNull(user.getEmail())){
            e = new UserExample();
            e.createCriteria().andEmailEqualTo(user.getEmail());
            list = userMapper.selectByExample(e);
        }else{
            throw new Exception("login by uid or email ?");
        }

        if (list != null && list.size() == 1) {
            User dbUser = list.get(0);
            String securityPass = MD5Util.queryMD5(user.getPass());
            if (securityPass.equals(dbUser.getPass()) && dbUser.getStatus().intValue() == User.STATUS_ACTIVE.intValue()) {
                user.setPass(null);
                user.setUuid(dbUser.getUuid());
                user.setUid(dbUser.getUid());
                user.setUname(dbUser.getUname());
                user.setEmail(dbUser.getEmail());
                user.setGender(dbUser.getGender());
                user.setMobile(dbUser.getMobile());
                user.setStatus(dbUser.getStatus());
                user.setCreateTime(dbUser.getCreateTime());
                user.setModifyTime(dbUser.getModifyTime());
                return true;
            } else {
                throw new Exception("incorrect pass or status invalid");
            }
        }else{
            throw new UserNotExistException("unexists user : uid = " + user.getUid() + ", email = " + user.getEmail());
        }
    }

}
