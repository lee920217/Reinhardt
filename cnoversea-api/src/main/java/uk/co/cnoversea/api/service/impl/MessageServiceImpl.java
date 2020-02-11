package uk.co.cnoversea.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.mapper.MessageMapper;
import uk.co.cnoversea.api.dao.model.Message;
import uk.co.cnoversea.api.service.IMessageService;
import uk.co.cnoversea.api.vo.MessageFetchParam;
import uk.co.cnoversea.api.vo.MessageUser;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Message sendMessage(Message message) {
        message.setMid(null);
        if(messageMapper.insertSelective(message) == 1){
            return message;
        }
        return null;
    }

    @Override
    public List<MessageUser> fetchMessageByTask(MessageFetchParam param) {
        return messageMapper.selectByFetchParam(param);
    }
}
