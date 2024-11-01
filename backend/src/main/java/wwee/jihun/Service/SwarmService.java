package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Service.Agents.AgentA;
import wwee.jihun.Service.Agents.AgentB;
import wwee.jihun.Service.Agents.AgentC;

@Service
public class SwarmService {
    private final AgentA agentA;
    private final AgentB agentB;
    private final AgentC agentC;
    private final KeywordService keywordService;

    public SwarmService(AgentA agentA, AgentB agentB, AgentC agentC, KeywordService keywordService) {
        this.agentA = agentA;
        this.agentB = agentB;
        this.agentC = agentC;
        this.keywordService = keywordService;
    }

    public Mono<String> generateCasualInstagramAd(CampaignEntity campaignEntity) {
        return keywordService.suggestKeywords(campaignEntity)
                .flatMap(keywords -> {
                    campaignEntity.setKeywords(keywords);
                    // Step 1: agentA를 통해 키워드와 제품 정보 생성
                    return agentA.generateKeywordsAndProductInfo(campaignEntity, keywords)
                            .flatMap(keywordsAndInfo ->
                                    // Step 2: agentB를 통해 인스타그램 피드 광고 형식 생성
                                    agentB.generateInstagramAdFormat(campaignEntity,keywordsAndInfo).flatMap(adFormat ->
                                            // Step 3: agentC를 통해 반말 형식으로 변환
                                            agentC.convertToCasualTone(campaignEntity,adFormat).map(casualAd ->
                                                    "광고 문구:\n" + casualAd
                                            )
                                    )
                            );
                });

    }

}