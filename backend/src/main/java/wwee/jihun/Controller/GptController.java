package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import wwee.jihun.Entity.GptEntity;
import wwee.jihun.Service.AwsLambdaService;
import wwee.jihun.Service.GptService;
import wwee.jihun.Service.JsonDecoderService;

// 스프링의 REST 컨트롤러, HTTP 요청을 처리하는 역할
@RestController
// /api/gpt 경로로 들어오는 요청 처리
@RequestMapping("/api/gpt")
// 다른 도메인에서 요청(CORS) 허용을 http://localhost:8081 로 설정
@CrossOrigin(origins = "http://localhost:8081")
public class GptController {
    // AwsLambdaService 클래스를 new 키워드를 통해 awsLambdaService 객체를 만듬 => 인스턴스화
    AwsLambdaService awsLambdaService = new AwsLambdaService();
    // JsonDecoderService 클래스를 new 키워드를 통해 jsonDecoderService 객체를 만듬
    JsonDecoderService jsonDecoderService = new JsonDecoderService();
    private final GptService gptService;
    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    // HTTP POST 요청 처리
    @PostMapping
    // 요청 본문으로 질문(Question) 을 문자열로 받아옴
    public String GenerateAnswer(@RequestBody String Question){
        try {
            // 전달받은 질문을 AWS Lambda 함수를 보내고, 그 함수에서 반환된 응답을 받아옴
            String response = awsLambdaService.RequestLambdaFunction(Question);
            // Lambda 함수로부터 받은 응답을 JSON 형태로 디코딩하고, 원하는 형식으로 변환하여 반환
            return jsonDecoderService.DecodeAndFormat(response);
        } catch (Exception e) {
            // Lambda 호출 중에 오류가 발생하면 예외를 발생시킴
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/chat")
    public Mono<String[]> Chat(@RequestBody GptEntity gptEntity) {
        String prompt = gptEntity.getPrompt();
        Mono<String> response = gptService.getChatResponse(prompt);
        return jsonDecoderService.DecodeAndFormatGpt(response);
    }

}
