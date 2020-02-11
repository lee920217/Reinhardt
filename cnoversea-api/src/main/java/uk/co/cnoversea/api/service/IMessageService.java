package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.Message;
import uk.co.cnoversea.api.vo.MessageFetchParam;
import uk.co.cnoversea.api.vo.MessageUser;

import java.util.List;

public interface IMessageService {

    Message sendMessage(Message message);
    List<MessageUser> fetchMessageByTask(MessageFetchParam param);

}
