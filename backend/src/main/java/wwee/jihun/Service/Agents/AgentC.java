package wwee.jihun.Service.Agents;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Prompt.TonePrompt;
import wwee.jihun.Service.GptService;

@Service
public class AgentC {
    private final GptService gptService;
    private final TonePrompt tonePrompt;
    public AgentC(GptService gptService, TonePrompt tonePrompt) {
        this.gptService = gptService;
        this.tonePrompt = tonePrompt;
    }
    public Mono<String> convertToCasualTone(CampaignEntity campaignEntity, String adFormat) {
        String prompt = "";
        if(campaignEntity.getTone().equals("럭키비키체")){
             prompt = String.format(
                    adFormat + "해당 광고 문구의 어조를 %s 형식으로 작성해줘 출력은 인사말 맺음말 뻬고 광고문구 3가지만 출력해줘 어조의 예시는 다음과 같아\n" +tonePrompt.getLuckviki(),
                    campaignEntity.getTone()
            );
        }else if(campaignEntity.getTone().equals("해요체")){
             prompt = String.format(
                    adFormat + "해당 광고 문구의 어조를 %s 형식으로 작성해줘 출력은 인사말 맺음말 뻬고 광고문구 3가지만 출력해줘 어조의 예시는 다음과 같아\n" +tonePrompt.getHaeyo(),
                    campaignEntity.getTone()
            );
        }else{
             prompt = String.format(
                    adFormat + "해당 광고 문구의 어조를 %s 형식으로 작성해줘 출력은 인사말 맺음말 뻬고 광고문구 3가지만 출력해줘 어조의 예시는 다음과 같아\n" +tonePrompt.getSeubnida(),
                    campaignEntity.getTone()
            );
        }
        return gptService.getChatResponse(prompt);
    }
}