package wwee.jihun.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.CampaignEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GptService {
    private final WebClient webClient;

    @Value("${gpt.api.key}")
    private String apiKey;

    public GptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<String> getChatResponse(String prompt, String systemMessage) {
        Map<String, Object> requestBody = Map.of(

                "model", "gpt-4o-mini",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 3000
        );

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
//                .bodyToMono(String.class) // 일단 String으로 받아서 로그로 확인
//                .doOnNext(response -> System.out.println("GPT Response: " + response)) // 로그 출력
                .map(response -> {
                    // JSON 파싱을 위해 다시 Map 형식으로 변환 시도
//                    ObjectMapper mapper = new ObjectMapper();
//                    Map<String, Object> parsedResponse = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
                    // 응답에서 텍스트 추출
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content"); // 텍스트 추출
                });
    }
}
