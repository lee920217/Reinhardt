package uk.co.cnoversea.api.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import uk.co.cnoversea.api.dao.model.PostcodeLngLatMap;
import uk.co.cnoversea.api.dao.model.PostcodeLngLatMapExample;

public interface PostcodeLngLatMapMapper {
    long countByExample(PostcodeLngLatMapExample example);

    int deleteByExample(PostcodeLngLatMapExample example);

    int deleteByPrimaryKey(Long mid);

    int insert(PostcodeLngLatMap record);

    int insertSelective(PostcodeLngLatMap record);

    List<PostcodeLngLatMap> selectByExample(PostcodeLngLatMapExample example);

    PostcodeLngLatMap selectByPrimaryKey(Long mid);

    int updateByExampleSelective(@Param("record") PostcodeLngLatMap record, @Param("example") PostcodeLngLatMapExample example);

    int updateByExample(@Param("record") PostcodeLngLatMap record, @Param("example") PostcodeLngLatMapExample example);

    int updateByPrimaryKeySelective(PostcodeLngLatMap record);

    int updateByPrimaryKey(PostcodeLngLatMap record);
}