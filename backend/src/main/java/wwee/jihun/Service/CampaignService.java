package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.CampaignEntity;

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

    //campaignId의 캠페인 내용 가져오기
    public List<CampaignEntity> getCampaignContent(String userId, Long campaignId) {
        return databaseService.getCampaignContentByUserIdAndCampaignId(userId, campaignId);
    }
    //userId 와 product 를 이용해 campaign 검색기능
    public List<CampaignEntity> getCampaignSearch(String userId, String product) {
        return databaseService.getCampaignSearch(userId, product);
    }
    // userID 와 campaignId 를 이용한 캠페인 목록 삭제기능 추가
    public void deleteCampaign(String userId, Long campaignId) {
        databaseService.deleteByUserIdAndCampaignId(userId, campaignId);
    }
}