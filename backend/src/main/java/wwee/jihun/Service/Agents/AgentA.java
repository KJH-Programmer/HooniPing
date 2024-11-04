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
    public Mono<String> generateKeywordsAndProductInfo(CampaignEntity campaignEntity, String keywords) {
        StringBuilder promptBuilder = new StringBuilder(String.format(
                "%s 브랜드의 %s 제품을 위한 광고 문구 3가지를 생성해줘." +
                        "광고 문구에 반드시 들어가야할 키워드는 %s이 들어가야해.",
                campaignEntity.getBrand(),
                campaignEntity.getProduct(),
                keywords
//                campaignEntity.getKeyword1(),
//                campaignEntity.getKeyword2(),
//                campaignEntity.getKeyword3()
        ));

        // 광고 모델이 있으면
        if (campaignEntity.getBrand_model() != null && !campaignEntity.getBrand_model().isBlank()) {
            promptBuilder.append("광고 모델 이름은 [").append(campaignEntity.getBrand_model()).append("]이야.\n");
        }
        if (campaignEntity.getFeatures() != null && !campaignEntity.getFeatures().isBlank()) {
            promptBuilder.append("제품의 특장점은 [").append(campaignEntity.getFeatures()).append("]이야. \n");
        }


        String prompt = promptBuilder.toString();
        String systemMessage = systemPrompt.getAgentASystemMessage();

        return gptService.getChatResponse(prompt, systemMessage);
    }
}