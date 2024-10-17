package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Service.CampaignService;

import java.util.List;

@RestController
@RequestMapping("/api/campaign")
@CrossOrigin(origins = "http://localhost:8081")
public class CampaignController {
    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

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

    //특정 캠페인 정보 조회
    @PostMapping("/content")
    public String CampaignContent(@RequestBody UserEntity userEntity){
        return campaignService.getCampaignContent(userEntity.getUserId());
    }

}
