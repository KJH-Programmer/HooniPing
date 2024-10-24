package wwee.jihun.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TavilySearchService {
    // Tavily API Key
    @Value("${tavily.api.key}")
    private String tavilyApiKey;

    private final RestTemplate restTemplate;

    // Tavily API의 기본 URL
    private static final String TAVILY_API_URL = "https://api.tavily.com/search";

    public TavilySearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> searchProduct(String product) {
        // 검색 쿼리 설정
        String query = product + " 광고";

        // Tavily API의 요청 본문 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("api_key", tavilyApiKey);
        requestBody.put("query", query);
        requestBody.put("search_depth", "basic");
        requestBody.put("include_answer", false);
        requestBody.put("include_images", false);
        requestBody.put("include_image_descriptions", false);
        requestBody.put("include_raw_content", false);
        requestBody.put("max_results", 5);
        requestBody.put("include_domains", new ArrayList<>());
        requestBody.put("exclude_domains", new ArrayList<>());

        // 헤더 설정 (필요한 경우 API 키 포함)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Tavily API로 요청 전송 및 응답 처리
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    TAVILY_API_URL,
                    HttpMethod.POST,
                    entity,
//                    Map.class
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            // API 응답에서 "results"의 "url" 추출
            return extractUrls(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // 빈 리스트 반환
        }
    }

    private List<String> extractUrls(Map<String, Object> response) {
        List<String> urls = new ArrayList<>();

        if (response != null && response.get("results") != null) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            for (Map<String, Object> result : results) {
                if (result.get("url") != null) {
                    urls.add(result.get("url").toString());
                }
            }
        }

        return urls;
    }
}
