package uk.co.cnoversea.api.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import uk.co.cnoversea.api.dao.model.Partner;
import uk.co.cnoversea.api.dao.model.PartnerExample;
import uk.co.cnoversea.api.vo.PartnerUser;

public interface PartnerMapper {
    long countByExample(PartnerExample example);

    int deleteByExample(PartnerExample example);

    int insert(Partner record);

    int insertSelective(Partner record);

    List<Partner> selectByExampleWithBLOBs(PartnerExample example);

    List<Partner> selectByExample(PartnerExample example);

    int updateByExampleSelective(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByExampleWithBLOBs(@Param("record") Partner record, @Param("example") PartnerExample example);

    int updateByExample(@Param("record") Partner record, @Param("example") PartnerExample example);

    List<PartnerUser> selectPartnerUserByTid(Long tid);
}