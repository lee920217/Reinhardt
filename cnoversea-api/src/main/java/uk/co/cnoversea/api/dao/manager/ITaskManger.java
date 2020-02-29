package uk.co.cnoversea.api.dao.manager;

import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;
import uk.co.cnoversea.api.vo.TaskExt;
import uk.co.cnoversea.web.vo.Page;

public interface ITaskManger {

    Page<TaskExt> pageQuery(TaskWithBLOBs record);

}
