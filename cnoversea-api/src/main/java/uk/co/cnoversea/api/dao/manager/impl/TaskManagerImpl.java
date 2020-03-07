package uk.co.cnoversea.api.dao.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.cnoversea.api.dao.manager.ITaskManager;
import uk.co.cnoversea.api.dao.mapper.PartnerMapper;
import uk.co.cnoversea.api.dao.mapper.TaskMapper;
import uk.co.cnoversea.api.dao.model.Partner;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;
import uk.co.cnoversea.api.vo.TaskExt;
import uk.co.cnoversea.web.vo.Page;

import java.util.List;

@Service
public class TaskManagerImpl implements ITaskManager {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private PartnerMapper partnerMapper;

    @Override
    public Page<TaskExt> pageQuery(TaskWithBLOBs record) {
        List<TaskExt> list = taskMapper.selectPage(record);
        Page<TaskExt> page = new Page<TaskExt>();
        page.setRows(list);
        page.setRecords(taskMapper.pageCount(record));
        page.setPageParameter(record);
        page.pageCalculation();
        return page;
    }

    @Override
    @Transactional
    public TaskWithBLOBs declareTask(TaskWithBLOBs record) {
        if(taskMapper.insertSelective(record) == 1){
            Partner partner = new Partner();
            partner.setTid(record.getTid());
            partner.setUuid(record.getUuid());
            partner.setPartnerUuid(record.getUuid());
            partner.setMessage("sponsor");
            if(partnerMapper.insertSelective(partner) == 1){
                return record;
            }
        }
        return null;
    }
}
