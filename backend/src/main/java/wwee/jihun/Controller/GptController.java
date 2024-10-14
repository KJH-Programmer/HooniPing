package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import wwee.jihun.Service.AwsLambdaService;

@RestController
@RequestMapping("/api/gpt")
@CrossOrigin(origins = "http://localhost:8081")
public class GptController {
    AwsLambdaService awsLambdaService = new AwsLambdaService();

    @PostMapping
    public String GenerateAnswer(@RequestBody String Question){
        try {
            return awsLambdaService.RequestLambdaFunction(Question);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
