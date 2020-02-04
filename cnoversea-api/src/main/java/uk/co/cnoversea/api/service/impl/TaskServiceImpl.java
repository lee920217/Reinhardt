package uk.co.cnoversea.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.mapper.PartnerMapper;
import uk.co.cnoversea.api.dao.mapper.TaskMapper;
import uk.co.cnoversea.api.dao.mapper.UserMapper;
import uk.co.cnoversea.api.dao.model.*;
import uk.co.cnoversea.api.service.ITaskService;

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

    @Override
    public TaskWithBLOBs declareTask(TaskWithBLOBs task) throws Exception {
        task.setTid(null);
        task.setCreateTime(null);
        task.setModifyTime(null);

        if(taskMapper.insertSelective(task) == 1){
            return task;
        }
        return null;
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
    public List<Partner> queryPartner(Task task) throws Exception {
        PartnerExample e = new PartnerExample();
        e.createCriteria().andTidEqualTo(task.getTid());
        List<Partner> list = partnerMapper.selectByExampleWithBLOBs(e);
        return list;
    }
}
