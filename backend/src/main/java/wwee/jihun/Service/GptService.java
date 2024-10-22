package wwee.jihun.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GptService {
    private final WebClient webClient;

    @Value("${gpt.api.key}")
    private String apiKey;

    public GptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<String> getChatResponse(String prompt) {
        String requestBody = String.format("""
                {
                    "model" : "gpt-4",
                    "messages" : [{"role": "user", "content": "%s"}],
                    "max_tokens": 150
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
}
