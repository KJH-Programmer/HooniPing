package wwee.jihun.Service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import wwee.jihun.Entity.CampaignEntity;

@Mapper(componentModel = "spring")
public interface CampaignMapper {
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    // 엔티티 자체를 업데이트하는 메서드
    void updateCampaign(CampaignEntity source, @MappingTarget CampaignEntity target);
}