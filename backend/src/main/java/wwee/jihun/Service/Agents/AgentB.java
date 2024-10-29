package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.TonePrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentB {
    private final GptService gptService;
    private final TonePrompt tonePrompt;
    public AgentB(GptService gptService, TonePrompt tonePrompt) {
        this.gptService = gptService;
        this.tonePrompt = tonePrompt;
    }
    public Mono<String> generateInstagramAdFormat(CampaignEntity campaignEntity,String keywordsAndInfo) {
        String snsSample = "- 텀블러 대충 씻고 계신가요? 한 알이면 충분해요 (제품 사용 상황 강조형)\n" +
                "- 손목에서 느껴지는 러블리한 슬림 스트랩 (제품 강조형)\n" +
                "- 복부가 조이지 않아 하루종일 편안한 출근바지 (제품 강조형)";

        String prompt = String.format(
                keywordsAndInfo +"해당 광고 문구를 %s에 어울리는 형식으로 작성해줘",
                campaignEntity.getAd_format(),
                "[예시]\n", snsSample
        );

        String systemMessage = "당신은 광고 카피라이팅 전문가입니다. " +
                "사용자가 제공하는 \"게시 형태\"에 따라 광고 문구를 수정하는 역할을 수행합니다. " +
                "\"게시 형태\"는 블로그, 인스타그램, 리뷰 중 하나입니다. " +
                "각 형태에 적합한 스타일과 어조를 고려하여 광고 문구를 작성해야 합니다. " +
                "예를 들어, SNS 피드의 경우 시각적으로 매력적이고 짧은 문구를 사용하고, 블로그에서는 더 자세하고 정보적인 내용을 포함해야 합니다. 리뷰 형식에서는 소비자의 목소리를 반영하여 신뢰를 주는 표현을 사용해야 합니다.\n" +
                "수정된 광고 문구는 제품이나 브랜드의 매력적인 메시지를 강조하고, 사용자가 원하는 게시 형태에 적합하도록 조정해야 합니다.\n";

        return gptService.getChatResponse(prompt, systemMessage);
    }
}