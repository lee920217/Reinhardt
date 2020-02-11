package uk.co.cnoversea.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.manager.ITaskManger;
import uk.co.cnoversea.api.dao.mapper.PartnerMapper;
import uk.co.cnoversea.api.dao.mapper.TaskMapper;
import uk.co.cnoversea.api.dao.mapper.UserMapper;
import uk.co.cnoversea.api.dao.model.*;
import uk.co.cnoversea.api.service.ITaskService;
import uk.co.cnoversea.api.vo.PartnerUser;
import uk.co.cnoversea.web.vo.Page;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private ITaskManger taskManger;

    @Override
    public TaskWithBLOBs declareTask(TaskWithBLOBs task) throws Exception {
        task.setTid(null);
        task.setCreateTime(null);
        task.setModifyTime(null);

        if(taskMapper.insertSelective(task) == 1){
            Partner partner = new Partner();
            partner.setTid(task.getTid());
            partner.setUuid(task.getUuid());
            partner.setPartnerUuid(task.getUuid());
            partner.setMessage("sponsor");
            if(partnerMapper.insertSelective(partner) == 1){
                return task;
            }
        }
        return null;
    }

    @Override
    public Page<TaskWithBLOBs> page(TaskWithBLOBs record) throws Exception {
        return taskManger.pageQuery(record);
    }

    @Override
    public Partner joinTask(Partner partner) throws Exception {
        TaskWithBLOBs task = taskMapper.selectByPrimaryKey(partner.getTid());
        if(task == null){
            throw new Exception("task not exists");
        }
        partner.setUuid(task.getUuid());
        if(partnerMapper.insertSelective(partner) == 1){
            return partner;
        }
        return null;
    }

    @Override
    public List<PartnerUser> queryPartner(Task task) throws Exception {
        List<PartnerUser> list = partnerMapper.selectPartnerUserByTid(task.getTid());
        return list;
    }

    @Override
    public List<TaskWithBLOBs> myJoinedTask(Partner partner) throws Exception {
        return taskMapper.selectByPartnerUUID(partner);
    }
}
