package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.SystemPrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentA {
    private final GptService gptService;
    private final SystemPrompt systemPrompt;
    public AgentA(GptService gptService, SystemPrompt systemPrompt) {
        this.gptService = gptService;
        this.systemPrompt = systemPrompt;
    }
    public Mono<String> generateKeywordsAndProductInfo(CampaignEntity campaignEntity) {
        String prompt = String.format(
                "%s 브랜드의 %s 제품을 위한 광고 문구 3가지를 생성해줘." +
                        "광고 문구에 반드시 들어가야할 키워드는 %s, %s, %s 이 들어가야해.",
                campaignEntity.getBrand(),
                campaignEntity.getProduct(),
                campaignEntity.getKeyword1(),
                campaignEntity.getKeyword2(),
                campaignEntity.getKeyword3()
        );

        String systemMessage = systemPrompt.getAgentASystemMessage();

        return gptService.getChatResponse(prompt, systemMessage);
    }
}