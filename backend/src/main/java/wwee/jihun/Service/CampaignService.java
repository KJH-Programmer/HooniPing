package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Repository.CampaignRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    private final DatabaseService databaseService;

    public CampaignService(DatabaseService databaseService) { this.databaseService = databaseService; }

    //db에 있는 모든 캠페인 반환
    public List<CampaignEntity> getAllCampaigns() {
        return databaseService.getAllCampaigns();
    }

    //userId의 모든 캠페인 반환
    public List<CampaignEntity> getUserAllCampaigns(String userId) {
        return databaseService.getUserAllCampaigns(userId);
    }

    //userId의 product 가져오기
    public String getCampaignContent(String userId) {
        Optional<CampaignEntity> campaignEntity = databaseService.findCampaignByUserIdAndCampaignId(userId);

        if(campaignEntity.isEmpty()){
            return "Campaign Not Found";
        } else {
            String product = campaignEntity.map(CampaignEntity::getProduct).orElse("");

            return "Product: "+ product + ", Keywords: ";
        }
    }
}
