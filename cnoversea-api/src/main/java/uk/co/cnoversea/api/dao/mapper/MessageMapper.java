package uk.co.cnoversea.api.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import uk.co.cnoversea.api.dao.model.Message;
import uk.co.cnoversea.api.dao.model.MessageExample;
import uk.co.cnoversea.api.vo.MessageFetchParam;

public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Long mid);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExampleWithBLOBs(MessageExample example);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Long mid);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExampleWithBLOBs(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectByFetchParam(MessageFetchParam param);
}