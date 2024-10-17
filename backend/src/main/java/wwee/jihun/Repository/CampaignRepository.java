package wwee.jihun.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wwee.jihun.Entity.CampaignEntity;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<CampaignEntity, String> {
    //캠페인 id를 확인
    Optional<CampaignEntity> findByUserId(String userId);
    List<CampaignEntity> findAllByUserId(String userId);
}
