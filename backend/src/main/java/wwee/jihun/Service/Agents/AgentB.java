package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.SystemPrompt;
import wwee.jihun.Prompt.TonePrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentB {
    private final GptService gptService;
    private final SystemPrompt systemPrompt;

    public Mono<String> generateAdFormat(CampaignEntity campaignEntity, String keywordsAndInfo) {
        // 광고 형식 확인 및 기본 값 설정
        String adFormat = campaignEntity.getAd_format();
        if (adFormat == null || adFormat.isBlank()) {
            adFormat = "기본 광고 문구";
        }

        // 프롬프트 구성
        String prompt;
        if ("기본 광고 문구".equals(adFormat)) {
            prompt = String.format(
                "%s 해당 광고 문구를 %s에 어울리는 형식으로 작성해줘.",
                keywordsAndInfo, adFormat
            );
        } else {
            // 광고 형식에 따른 예시 불러오기
            String formExample = switch (adFormat) {
                case "인스타그램" -> formPrompt.getSns();
                case "블로그" -> formPrompt.getBlog();
                default -> throw new IllegalArgumentException("Invalid ad format: " + adFormat);
            };

            prompt = String.format(
                "%s 해당 광고 문구를 %s에 어울리는 형식으로 작성해줘. 형식의 예시는 다음과 같아:\n%s",
                keywordsAndInfo, adFormat, formExample
            );
        }

        String systemMessage = systemPrompt.getAgentBSystemMessage();

        return gptService.getChatResponse(prompt, systemMessage);
    }
}