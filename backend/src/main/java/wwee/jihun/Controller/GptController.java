package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.GptService;
import wwee.jihun.Service.JsonDecoderService;
import wwee.jihun.Service.SwarmService;

// 스프링의 REST 컨트롤러, HTTP 요청을 처리하는 역할
@RestController
// /api/gpt 경로로 들어오는 요청 처리
@RequestMapping("/api/gpt")
// 다른 도메인에서 요청(CORS) 허용을 http://localhost:8081 로 설정
@CrossOrigin(origins = "http://localhost:8081")
public class GptController {
    // JsonDecoderService 클래스를 new 키워드를 통해 jsonDecoderService 객체를 만듬
    JsonDecoderService jsonDecoderService = new JsonDecoderService();
    private final GptService gptService;
    private final SwarmService swarmService;
    public GptController(GptService gptService, SwarmService swarmService) {
        this.gptService = gptService;
        this.swarmService = swarmService;
    }

//    @PostMapping("/keyword")
//    public Mono<String[]> Chat(@RequestBody CampaignEntity campaignEntity) {
//        String prompt = campaignEntity.getPrompt_for_ad_text();
//        Mono<String> response = gptService.getChatResponse(prompt, systemMessage);
//        return jsonDecoderService.DecodeAndFormatGpt(response);
//    }

    @PostMapping("/adtext")
    public Mono<String> AdText(@RequestBody CampaignEntity campaignEntity) {
        return swarmService.generateCasualInstagramAd(campaignEntity);
    }

}
