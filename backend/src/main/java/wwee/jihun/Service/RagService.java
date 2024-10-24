package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {
    private final WebClient webClient;

    public RagService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.tavily.com")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<List<String>> callTavilyModel(String query) {
        if (query == null || query.length() < 5) {
            return Mono.error(new IllegalArgumentException("Query is too short. Min query length is 5 characters."));
        }

        return webClient.post()
                .uri("/search")
                .bodyValue(Map.of(
                        "api_key", "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",
                        "query", query))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("API Error: " + errorBody);
                                    return Mono.error(new RuntimeException("API 요청 실패: " + errorBody));
                                })
                )
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                    if (results == null || results.isEmpty()) {
                        return Mono.error(new RuntimeException("Response did not contain 'results' field or it was empty"));
                    }

                    // results에서 url 필드만 추출
                    List<String> urls = results.stream()
                            .map(result -> (String) result.get("url"))
                            .collect(Collectors.toList());

                    return Mono.just(urls);
                });
    }
}