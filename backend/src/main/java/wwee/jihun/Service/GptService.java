package wwee.jihun.Service;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.JwtToken.TokenProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GptService {
    private final WebClient webClient;
    private final RagService ragService;
    private final WebCrawlingService webCrawlingService;
    private final KoreanTextService koreanTextService;

    @Value("${gpt.api.key}")
    private String apiKey;

    public GptService(WebClient.Builder webClientBuilder, RagService ragService, KoreanTextService koreanTextService, WebCrawlingService webCrawlingService) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.ragService = ragService;
        this.koreanTextService = koreanTextService;
        this.webCrawlingService = webCrawlingService;
    }

    public Mono<String> getChatResponse(String prompt) {
        String requestBody = String.format("""
                {
                    "model" : "gpt-4",
                    "messages" : [{"role": "user", "content": "%s"}],
                    "max_tokens": 150
                }
                """, prompt +"광고에 대해서 키워드 3가지를 생성해줘 형식은 json 형식으로 key값을 keyword1, keyword2, keyword3으로 지정해서 한글로 답변해줘");

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getAdText(CampaignEntity campaignEntity) {
        String prompt = campaignEntity.getKeyword1() + " " + campaignEntity.getKeyword2() + " " + campaignEntity.getKeyword3()+
                "이 3가지의 키워드를 가지고"+campaignEntity.getProduct()+"에 대한 광고 문구를 최소 3가지 만들어줘 광고할 화장품의 브랜드는"+campaignEntity.getBrand()+
                "이고 각 키워드에 문구 한개씩 총 문구 3가지를 만들고 양식은 json 형태로 key값은 keyword1, keyword2, keyword3으로 해서 만들어줘";
        String requestBody = String.format("""
                {
                    "model" : "gpt-4",
                    "messages" : [{"role": "user", "content": "%s"}],
                    "max_tokens": 500
                }
                """, prompt);

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

    }

    public String getKeyword(String product){
        //tavily로 product 연관된 웹사이트 url 가지고옴
        List<String> urlList = ragService.callTavilyModel(product).block();

        //tavily로 가져온 url 크롤링
        List<String> crawling = new ArrayList<>();
        for (int i = 0; i<urlList.size(); i++){
            crawling.add(webCrawlingService.crawlByUserAndProduct(urlList.get(i)));
        }

        //크롤링 한 html에서 단어 추출
        List<List<String>> tokenList = new ArrayList<>();
        for(int i = 0; i<crawling.size(); i++){
            tokenList.add(koreanTextService.extractKeywords(crawling.get(i)));
        }



        return " ";
    }
}


