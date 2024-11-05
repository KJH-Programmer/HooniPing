package wwee.jihun.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Repository.CampaignMapper;
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

    // 새로운 캠패인에 campgaignId 할당: max+1
    public Long getNewCampaignId(String userId) {
        Long maxCampaignId = databaseService.getMaxCampaignIdForUser(userId);
        return maxCampaignId + 1;
    }
    //새로운 캠페인 내용 저장
    public CampaignEntity saveNewCampaign(String userId, CampaignEntity newCampaign) {
        // 프론트에서 받아온 campaignId
        Long newCampaignId = newCampaign.getCampaignId();

        // userId에게 newCampaignId가 있는지 확인
        boolean exists = campaignRepository.existsByUserIdAndCampaignId(userId, newCampaignId);
        // newCampaignId가 있다면 200 ok 응답 반환
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Campaign ID already exists for this user.");
        }
        // newCampaignId가 없으면 새로운 캠페인으로 저장
        newCampaign.setCampaignId(newCampaignId);
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
