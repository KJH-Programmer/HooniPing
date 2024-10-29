package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.GptService;

@Service
public class AgentA {
    private final GptService gptService;
    public AgentA(GptService gptService) {
        this.gptService = gptService;
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

        String systemMessage = "당신은 광고 카피라이팅 전문가로서 사용자로부터 제공받은 제품에 적합한 키워드를 제안하고, 그 키워드를 포함한 광고 문구 3가지를 작성하는 역할을 수행합니다. " +
                "각 광고 문구는 짧고 임팩트 있게 작성하며, 소비자에게 제품의 장점과 가치를 효과적으로 전달해야 합니다. " +
                "제품의 특성과 타겟 소비자를 고려해, 창의적이면서도 제품에 맞는 어휘와 문장을 사용해 주세요." +
                "광고 문구에는 제품의 장점을 확 드러내고, 소비자가 제품이 필요한 상황을 이해할 수 있도록 유도하며, 브랜드 메시지를 강조해야 합니다.\n" +
                "카피의 독자는 고객! 고객 입장에서 작성하세요.\n" +
                "제품이 고객의 어떤 문제를 해결할 수 있는지 알려주세요.\n" +
                "정말 이 제품이 고객에게 큰 ‘가치‘를 줄 거라는 확신을 주세요.";

        return gptService.getChatResponse(prompt, systemMessage);
    }
}
