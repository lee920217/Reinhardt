package uk.co.cnoversea.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.mapper.UserMapper;
import uk.co.cnoversea.api.dao.model.User;
import uk.co.cnoversea.api.dao.model.UserExample;
import uk.co.cnoversea.api.service.IUserService;
import uk.co.cnoversea.common.security.MD5Util;
import uk.co.cnoversea.common.string.Simple;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User regist(User user) throws Exception {
        if (Simple.StringNull(user.getUid())) {
            throw new Exception("invalid uid : " + user.getUid());
        }

        user.setCreateTime(null);
        user.setModifyTime(null);

        user.setUuid(Simple.genUUID32());
        String pass = user.getPass();
        pass = MD5Util.queryMD5(pass);
        user.setPass(pass);
        if (userMapper.insertSelective(user) == 1) {
            user.setPass(null);
            return user;
        }
        return null;
    }

    @Override
    public boolean login(User user) throws Exception {
        UserExample e = new UserExample();
        e.createCriteria().andUidEqualTo(user.getUid());
        List<User> list = userMapper.selectByExample(e);
        if (list != null && list.size() == 1) {
            User dbUser = list.get(0);
            String securityPass = MD5Util.queryMD5(user.getPass());
            if (securityPass.equals(dbUser.getPass())) {
                return true;
            } else {
                throw new Exception("incorrect pass");
            }
        }else{
            throw new Exception("unexists user : " + user.getUuid());
        }
    }
}
