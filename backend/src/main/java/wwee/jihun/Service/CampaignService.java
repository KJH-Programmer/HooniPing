package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Repository.CampaignRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    private final DatabaseService databaseService;
    private final CampaignRepository campaignRepository;
    public CampaignService(DatabaseService databaseService, CampaignRepository campaignRepository) {
        this.databaseService = databaseService;
        this.campaignRepository = campaignRepository;
    }

    //db에 있는 모든 캠페인 반환
    public List<CampaignEntity> getAllCampaigns() {
        return databaseService.getAllCampaigns();
    }

    //userId의 모든 캠페인 반환
    public List<CampaignEntity> getUserAllCampaigns(String userId) {
        return databaseService.getUserAllCampaigns(userId);
    }

    //campaignId의 캠페인 내용 가져오기
    public Optional<CampaignEntity> getCampaignContent(String userId, Long campaignId) {
        return databaseService.getCampaignContentByUserIdAndCampaignId(userId, campaignId);
    }

    //새로운 캠페인에 campaignId를 할당해서 캠페인 내용 저장
    public CampaignEntity saveNewCampaign(String userId, CampaignEntity newCampaign) {
        Long maxCampaignId = databaseService.getMaxCampaignIdForUser(userId);
        Long nextCampaignId = maxCampaignId + 1;
        newCampaign.setCampaignId(nextCampaignId);
        return campaignRepository.save(newCampaign);
    }

    //userId, campaignId인 캠페인 내용 업데이트
    public CampaignEntity updateCampaign(String userId, Long campaignId, CampaignEntity updatedCampaign) {
        //userId, campaignId인 기존 엔티티 조회
        CampaignEntity existingCampaign = databaseService.getCampaignContentByUserIdAndCampaignId(userId, campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found for userId: " + userId + " and campaignId: " + campaignId));
        // 기존 엔티티 -> 업데이트 엔티티
        CampaignMapper.INSTANCE.updateCampaign(updatedCampaign, existingCampaign);

        // 변경된 엔티티를 저장하고 반환
        return campaignRepository.save(existingCampaign);
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
