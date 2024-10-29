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

        String systemMessage = "당신은 광고 카피라이팅 전문가로서, 사용자가 제공하는 \"말투\"에 따라 광고 문구를 자연스럽게 수정하는 역할을 맡고 있습니다. " +
                "\"말투\"는 해요체, 습니다체, 반말, 럭키비키체, 신조어 중 하나입니다. " +
                "주어진 광고 문구의 문맥을 고려하여 적절한 말투로 수정하되, 억지로 끼워맞추는 느낌이 들지 않도록 해야 합니다. " +
                "수정이 필요할 경우 그 정도를 판단하여 자연스럽게 표현해 주세요.\n" +
                "광고 문구는 소비자가 제품에 공감할 수 있도록 유도하고, 제품의 장점을 자연스럽게 드러내야 합니다.\n";

        return gptService.getChatResponse(prompt, systemMessage);
    }
}
