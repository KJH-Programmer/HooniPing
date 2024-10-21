package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import wwee.jihun.Service.AwsLambdaService;
import wwee.jihun.Service.JsonDecoderService;

@RestController
@RequestMapping("/api/gpt")
@CrossOrigin(origins = "http://localhost:8081")
public class GptController {
    AwsLambdaService awsLambdaService = new AwsLambdaService();
    JsonDecoderService jsonDecoderService = new JsonDecoderService();

    @PostMapping
    public String GenerateAnswer(@RequestBody String Question){
        try {

            String response = awsLambdaService.RequestLambdaFunction(Question);
            return jsonDecoderService.DecodeAndFormat(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
