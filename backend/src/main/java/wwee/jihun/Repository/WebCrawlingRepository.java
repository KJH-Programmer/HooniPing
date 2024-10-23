package wwee.jihun.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwee.jihun.Entity.CampaignEntity;

import java.util.List;

public interface WebCrawlingRepository extends JpaRepository<CampaignEntity, Long> {
    // userId 와 Product 조회 후 웹크롤링 기능 추가
    List<CampaignEntity> findAllByUserIdAndProduct(String userId, String product);
}