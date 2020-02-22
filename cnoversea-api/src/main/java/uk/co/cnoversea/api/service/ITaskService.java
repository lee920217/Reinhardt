package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.Partner;
import uk.co.cnoversea.api.dao.model.Task;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;
import uk.co.cnoversea.api.vo.PartnerUser;
import uk.co.cnoversea.web.vo.Page;

import java.util.List;

public interface ITaskService {
    TaskWithBLOBs declareTask(TaskWithBLOBs task) throws Exception;
    Page<TaskWithBLOBs> page(TaskWithBLOBs page) throws Exception;
    Partner joinTask(Partner partner) throws Exception;
    Partner secedeTask(Partner partner) throws Exception;
    List<PartnerUser> queryPartner(Task task) throws Exception;
    List<TaskWithBLOBs> myJoinedTask(Partner partner) throws Exception;
}
