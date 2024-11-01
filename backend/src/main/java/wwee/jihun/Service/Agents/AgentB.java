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

    public AgentB(GptService gptService, TonePrompt tonePrompt, SystemPrompt systemPrompt) {
        this.gptService = gptService;
        this.systemPrompt = systemPrompt;
    }
    public Mono<String> generateInstagramAdFormat(CampaignEntity campaignEntity,String keywordsAndInfo) {

        String adFormat = campaignEntity.getAd_format();
        if (adFormat == null || adFormat.isBlank()) {
            adFormat = "기본 광고 문구";
        }
        String prompt = String.format(
                keywordsAndInfo +"\n 해당 광고 문구를 %s에 어울리는 형식으로 작성해줘.",
                adFormat
        );

        String systemMessage = systemPrompt.getAgentBSystemMessage();

        return gptService.getChatResponse(prompt, systemMessage);
    }
}