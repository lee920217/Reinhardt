package uk.co.cnoversea.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import uk.co.cnoversea.api.dao.model.Partner;
import uk.co.cnoversea.api.dao.model.Task;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;
import uk.co.cnoversea.api.service.ITaskService;
import uk.co.cnoversea.api.service.IUserService;
import uk.co.cnoversea.web.controller.AbstractController;
import uk.co.cnoversea.web.vo.Page;
import uk.co.cnoversea.web.vo.RequestVO;
import uk.co.cnoversea.web.vo.ResponseVO;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController {

    private final static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskService taskService;

    //发布行程
    @RequestMapping(value = "/declare", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<TaskWithBLOBs> declare(@RequestBody RequestVO<TaskWithBLOBs> param) {
        TaskWithBLOBs task = param.getQuery();
        TaskWithBLOBs taskRet = null;
        try {
            taskRet = taskService.declareTask(task);
            return genResponse(taskRet);
        } catch (Exception e) {
            logger.error("declare task fail", e);
            return genResponse(ResponseVO.CODE_ERR, "declare task fail, " + e.getMessage() , task);
        }
    }

    //查询行程
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<Page<TaskWithBLOBs>> page(@RequestBody RequestVO<TaskWithBLOBs> param) {
        TaskWithBLOBs task = param.getQuery();
        Page<TaskWithBLOBs> page = null;
        try {
            page = taskService.page(task);
            return genResponse(page);
        } catch (Exception e) {
            logger.error("declare task fail", e);
            return genResponse(ResponseVO.CODE_ERR, "declare task fail, " + e.getMessage() , page);
        }
    }

    //加入行程
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<Partner> join(@RequestBody RequestVO<Partner> param) {
        Partner user = param.getQuery();
        Partner userRet = null;
        try {
            userRet = taskService.joinTask(user);
            return genResponse(userRet);
        } catch (Exception e) {
            logger.error("join task fail", e);
            if (e instanceof DuplicateKeyException) {
                return genResponse(ResponseVO.CODE_ERR, "repeat join, taskId : " + user.getTid(), user);
            } else {
                return genResponse(ResponseVO.CODE_ERR, "join fail, " + e.getMessage() , user);
            }
        }
    }

    //查询同行人
    @RequestMapping(value = "/partners", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<List<Partner>> partners(@RequestBody RequestVO<Task> param) {
        Task task = param.getQuery();
        List<Partner> taskRet = null;
        try {
            taskRet = taskService.queryPartner(task);
            return genResponse(taskRet);
        } catch (Exception e) {
            logger.error("query partners fail", e);
            return genResponse(ResponseVO.CODE_ERR, "query partner fail, " + e.getMessage() , taskRet);
        }
    }

    //查询参与过的行程
    @RequestMapping(value = "/joinedTask", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO<List<TaskWithBLOBs>> joinedTask(@RequestBody RequestVO<Partner> param) {
        Partner partner = param.getQuery();
        List<TaskWithBLOBs> taskRet = null;
        try {
            taskRet = taskService.myJoinedTask(partner);
            return genResponse(taskRet);
        } catch (Exception e) {
            logger.error("query partners fail", e);
            return genResponse(ResponseVO.CODE_ERR, "query partner fail, " + e.getMessage() , taskRet);
        }
    }
}
