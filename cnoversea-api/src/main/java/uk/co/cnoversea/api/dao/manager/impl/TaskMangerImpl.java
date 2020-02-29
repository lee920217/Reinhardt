package uk.co.cnoversea.api.dao.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.cnoversea.api.dao.manager.ITaskManger;
import uk.co.cnoversea.api.dao.mapper.TaskMapper;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;
import uk.co.cnoversea.api.vo.TaskExt;
import uk.co.cnoversea.web.vo.Page;

import java.util.List;

@Service
public class TaskMangerImpl implements ITaskManger {

    @Autowired
    private TaskMapper taskMapper;

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
}
