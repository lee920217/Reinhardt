package uk.co.cnoversea.api.service;

import uk.co.cnoversea.api.dao.model.Partner;
import uk.co.cnoversea.api.dao.model.Task;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;

import java.util.List;

public interface ITaskService {
    TaskWithBLOBs declareTask(TaskWithBLOBs task) throws Exception;
    Partner joinTask(Partner partner) throws Exception;
    List<Partner> queryPartner(Task task) throws Exception;
}
