package uk.co.cnoversea.api.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import uk.co.cnoversea.api.dao.model.Task;
import uk.co.cnoversea.api.dao.model.TaskExample;
import uk.co.cnoversea.api.dao.model.TaskWithBLOBs;

public interface TaskMapper {
    long countByExample(TaskExample example);

    int deleteByExample(TaskExample example);

    int deleteByPrimaryKey(Long tid);

    int insert(TaskWithBLOBs record);

    int insertSelective(TaskWithBLOBs record);

    List<TaskWithBLOBs> selectByExampleWithBLOBs(TaskExample example);

    List<Task> selectByExample(TaskExample example);

    TaskWithBLOBs selectByPrimaryKey(Long tid);

    int updateByExampleSelective(@Param("record") TaskWithBLOBs record, @Param("example") TaskExample example);

    int updateByExampleWithBLOBs(@Param("record") TaskWithBLOBs record, @Param("example") TaskExample example);

    int updateByExample(@Param("record") Task record, @Param("example") TaskExample example);

    int updateByPrimaryKeySelective(TaskWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TaskWithBLOBs record);

    int updateByPrimaryKey(Task record);
}