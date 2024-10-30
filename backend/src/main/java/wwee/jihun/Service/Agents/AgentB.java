package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.FormPrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentB {
    private final GptService gptService;
    private final FormPrompt formPrompt;

    public AgentB(GptService gptService, FormPrompt formPrompt) {
        this.gptService = gptService;
        this.formPrompt = formPrompt;
    }
    public Mono<String> generateInstagramAdFormat(CampaignEntity campaignEntity,String keywordsAndInfo) {
        // ad_format 을 기반으로 formPrompt 에서 형식을 가져옴
        String formExample = switch (campaignEntity.getAd_format()) {
            case "인스타그램" -> formPrompt.getSns();
            case "블로그" -> formPrompt.getBlog();
            default -> throw new IllegalArgumentException("잘못된 광고 형식: " + campaignEntity.getAd_format());
        };
        // 최종 프롬프트 구성
        String prompt = String.format("%s 광고 문구를 %s에 어울리는 형식으로 작성해줘. 형식의 예시는 다음과 같아:\n%s",
                keywordsAndInfo, campaignEntity.getAd_format(), formExample);

        return gptService.getChatResponse(prompt);
    }
}