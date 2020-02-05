package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.Message;
import uk.co.cnoversea.api.vo.MessageFetchParam;

import java.util.List;

public interface IMessageService {

    Message sendMessage(Message message);
    List<Message> fetchMessageByTask(MessageFetchParam param);

}
