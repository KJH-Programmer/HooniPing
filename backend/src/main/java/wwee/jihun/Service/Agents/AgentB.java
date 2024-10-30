package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.FormPrompt;
import wwee.jihun.Prompt.SystemPrompt;
import wwee.jihun.Prompt.TonePrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentB {
    private final GptService gptService;
    private final SystemPrompt systemPrompt;
    private final FormPrompt formPrompt;

    public AgentB(GptService gptService, TonePrompt tonePrompt, SystemPrompt systemPrompt, FormPrompt formPrompt) {
        this.gptService = gptService;
        this.systemPrompt = systemPrompt;
        this.formPrompt = formPrompt;
    }

    public Mono<String> generateInstagramAdFormat(CampaignEntity campaignEntity, String keywordsAndInfo) {
        // 광고 형식에 따른 예시 불러오기
        String formExample = switch (campaignEntity.getAd_format()) {
            case "인스타그램" -> formPrompt.getSns();
            case "블로그" -> formPrompt.getBlog();
            default -> throw new IllegalArgumentException("Invalid ad format: " + campaignEntity.getAd_format());
        };

        // 프롬프트 구성
        String prompt = String.format(
                keywordsAndInfo + " 해당 광고 문구를 %s에 어울리는 형식으로 작성해줘. 형식의 예시는 다음과 같아:\n%s",
                campaignEntity.getAd_format(), formExample
        );

        String systemMessage = systemPrompt.getAgentBSystemMessage();

        return gptService.getChatResponse(prompt, systemMessage);
    }
}
