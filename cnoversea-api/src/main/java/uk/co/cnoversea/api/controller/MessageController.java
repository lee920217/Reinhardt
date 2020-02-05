package uk.co.cnoversea.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.cnoversea.api.dao.model.Message;
import uk.co.cnoversea.api.service.IMessageService;
import uk.co.cnoversea.api.vo.MessageFetchParam;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private IMessageService messageService;

    //发布行程
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<Message> declare(@RequestBody RequestVO<Message> param) {
        Message message = param.getQuery();
        Message messageRet = null;
        try {
            messageRet = messageService.sendMessage(message);
            return genResponse(messageRet);
        } catch (Exception e) {
            logger.error("send message fail", e);
            return genResponse(ResponseVO.CODE_ERR, "send message fail, " + e.getMessage() , message);
        }
    }

    //查询同行人
    @RequestMapping(value = "/fetch", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<List<Message>> fetch(@RequestBody RequestVO<MessageFetchParam> param) {
        MessageFetchParam fetchParam = param.getQuery();
        List<Message> messageList = null;
        try {
            messageList = messageService.fetchMessageByTask(fetchParam);
            return genResponse(messageList);
        } catch (Exception e) {
            logger.error("fetch message fail", e);
            return genResponse(ResponseVO.CODE_ERR, "query partner fail, " + e.getMessage() , messageList);
        }
    }

}
