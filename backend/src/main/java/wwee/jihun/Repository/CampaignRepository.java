package wwee.jihun.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wwee.jihun.Entity.CampaignEntity;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<CampaignEntity, String> {
    //userId로 캠페인 엔티티 하나 찾기
    Optional<CampaignEntity> findByUserId(String userId);

    // userId로 캠페인 엔티티 리스트 찾기
    List<CampaignEntity> findAllByUserId(String userId);

    // userId와 campaignId로 캠페인 엔티티 리스트 찾기
    List<CampaignEntity> findAllByUserIdAndCampaignId(String userId, Long campaignId);

    //
    @Query("SELECT COALESCE(MAX(c.campaignId), 0) FROM CampaignEntity c WHERE c.userId = :userId")
    Long findMaxCampaignIdByUserId(String userId);
}
