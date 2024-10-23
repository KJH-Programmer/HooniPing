package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.CampaignService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaign")
@CrossOrigin(origins = {"http://localhost:8081"})
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // ***************<로그인 된 사용자(userId)의 캠페인 관리>****************

    //==================전체 캠페인 관리 페이지======================
    //모든 캠페인 조회
    @GetMapping
    public List<CampaignEntity> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    //userId의 전체 캠페인 조회
    @PostMapping("/user-campaign")
    public List<CampaignEntity> getUserAllCampaigns(@RequestBody CampaignEntity campaignEntity) {
        return campaignService.getUserAllCampaigns(campaignEntity.getUserId());
    }

    //=================특정 캠페인(campaignId) 관리 페이지====================
    //campaignId의 내용 조회
    @PostMapping("/content")
    public Optional<CampaignEntity> CampaignContent(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        Long campaignId = campaignEntity.getCampaignId();

        return campaignService.getCampaignContent(userId, campaignId);
    }

    //새로운 campaignId의 내용(사용자 입력, 생성된 콘텐츠) 저장
    @PostMapping("/content/save")
    public ResponseEntity<CampaignEntity> saveCampaign(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        CampaignEntity savedCampaign = campaignService.saveNewCampaign(userId, campaignEntity);
        return ResponseEntity.ok(savedCampaign);
    }

    @PutMapping("/content/update")
    public ResponseEntity<CampaignEntity> updateCampaign(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        Long campaignId = campaignEntity.getCampaignId();
        CampaignEntity updatedCampaign = campaignService.updateCampaign(userId, campaignId, campaignEntity);
        return ResponseEntity.ok(updatedCampaign);
    }

    //userId 와 product 를 이용해 campaign 검색기능
    @PostMapping ("/search")
    public List<CampaignEntity> CampaignSearch(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        String product = campaignEntity.getProduct();

        return campaignService.getCampaignSearch(userId, product);
    }
    // userID 와 campaignId 를 이용한 캠페인 목록 삭제기능
    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCampaign(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        Long campaignId = campaignEntity.getCampaignId();

        // 서비스에서 캠페인 삭제 실행
        campaignService.deleteCampaign(userId, campaignId);

        // 삭제 성공 시 204 No Content 반환
        return ResponseEntity.noContent().build();
    }
}