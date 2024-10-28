package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.GptService;

@Service
public class AgentC {
    private final GptService gptService;
    public AgentC(GptService gptService) {
        this.gptService = gptService;
    }
    public Mono<String> convertToCasualTone(CampaignEntity campaignEntity, String adFormat) {
        String prompt = String.format(
                adFormat + "해당 광고 문구의 어조를 %s 형식으로 작성해줘",
                campaignEntity.getTone()
        );
        return gptService.getChatResponse(prompt);
    }
}
