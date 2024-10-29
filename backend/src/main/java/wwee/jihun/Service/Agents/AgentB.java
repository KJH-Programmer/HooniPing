package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.GptService;

@Service
public class AgentB {
    private final GptService gptService;
    public AgentB(GptService gptService) {
        this.gptService = gptService;
    }
    public Mono<String> generateInstagramAdFormat(CampaignEntity campaignEntity,String keywordsAndInfo) {
        String prompt = String.format(
                keywordsAndInfo +"해당 광고 문구를 %s에 어울리는 형식으로 작성해줘",
                campaignEntity.getAd_format()
        );
        return gptService.getChatResponse(prompt);
    }
}