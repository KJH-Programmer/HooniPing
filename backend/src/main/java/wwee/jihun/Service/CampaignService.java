package wwee.jihun.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Repository.CampaignMapper;
import wwee.jihun.Repository.CampaignRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    @PersistenceContext
    private EntityManager entityManager;
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
        return databaseService.getUserAllCampaigns(userId).stream()
                .sorted(Comparator.comparing(CampaignEntity::getCampaignId).reversed())
                .collect(Collectors.toList());

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
    @Transactional
    public CampaignEntity updateCampaign(CampaignEntity campaignEntity) {
        // 기존 엔티티 조회
        CampaignEntity existingCampaign = entityManager.createQuery(
                        "SELECT c FROM CampaignEntity c WHERE c.userId = :userId AND c.campaignId = :campaignId", CampaignEntity.class)
                .setParameter("userId", campaignEntity.getUserId())
                .setParameter("campaignId", campaignEntity.getCampaignId())
                .getSingleResult();

        // 엔티티가 존재하지 않으면 예외 발생
        if (existingCampaign == null || !existingCampaign.getUserId().equals(campaignEntity.getUserId())) {
            throw new RuntimeException("Campaign not found or userId does not match");
        }

        // 필요한 필드만 수동으로 설정
        existingCampaign.setProduct(campaignEntity.getProduct());
        existingCampaign.setKeywords(campaignEntity.getKeywords());
        existingCampaign.setBrand(campaignEntity.getBrand());
        existingCampaign.setBrand_model(campaignEntity.getBrand_model());
        existingCampaign.setTone(campaignEntity.getTone());
        existingCampaign.setFeatures(campaignEntity.getFeatures());
        existingCampaign.setAd_text(campaignEntity.getAd_text());
        existingCampaign.setImage_url(campaignEntity.getImage_url());

        // 변경된 엔티티를 저장하고 반환
        return existingCampaign;
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
