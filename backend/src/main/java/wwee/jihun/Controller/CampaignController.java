package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Service.CampaignService;

import java.util.List;

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
    public List<CampaignEntity> getUserAllCampaigns(@RequestBody CampaignEntity campaignEntity){
        return campaignService.getUserAllCampaigns(campaignEntity.getUserId());
    }

    //=================특정 캠페인(campaignId) 관리 페이지====================
    //campaignId의 내용 조회
    @PostMapping("/content")
    public List<CampaignEntity> CampaignContent(@RequestBody CampaignEntity campaignEntity){
        String userId = campaignEntity.getUserId();
        Long campaignId = campaignEntity.getCampaignId();

        return campaignService.getCampaignContent(userId, campaignId);
    }

    //새로운 campaignId의 내용(사용자 입력, 생성된 콘텐츠) 저장
    @PostMapping("/content/save")
    public ResponseEntity<CampaignEntity> saveCampaign(@RequestBody CampaignEntity campaignEntity){
        String userId = campaignEntity.getUserId();
        CampaignEntity savedCampaign = campaignService.saveNewCampaign(userId, campaignEntity);
        return ResponseEntity.ok(savedCampaign);
    }

    //userId 와 product 를 이용해 campaign 검색기능
    @PostMapping ("/search")
    public List<CampaignEntity> CampaignSearch(@RequestBody CampaignEntity campaignEntity) {
        String userId = campaignEntity.getUserId();
        String product = campaignEntity.getProduct();

        return campaignService.getCampaignSearch(userId, product);
    }
}