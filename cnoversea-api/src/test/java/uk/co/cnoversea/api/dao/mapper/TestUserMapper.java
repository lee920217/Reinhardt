package uk.co.cnoversea.api.dao.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.cnoversea.Application;
import uk.co.cnoversea.api.dao.model.User;
import uk.co.cnoversea.common.security.MD5Util;
import uk.co.cnoversea.common.string.Simple;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration
public class TestUserMapper {

    private static final Logger logger = LoggerFactory.getLogger(TestUserMapper.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertSelective() {
        String uuid = Simple.genUUID32();
        Date date = new Date();

        User user = new User();
        user.setUuid(uuid);
        user.setUid(uuid);
        user.setUname(uuid);
        user.setEmail(uuid+"@163.com");
        user.setMobile("15912345678");
        user.setPass(MD5Util.queryMD5("123456"));
        user.setStatus(Byte.valueOf("1"));
        user.setCreateTime(date);
        user.setModifyTime(date);
        int ret = userMapper.insert(user);
        Assert.assertEquals(ret, 1);
    }

}
