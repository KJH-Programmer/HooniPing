package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

@Service
public class AwsLambdaService {
    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper;

    public AwsLambdaService(){
        this.lambdaClient = LambdaClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("cloude2")).build();
        this.objectMapper = new ObjectMapper();
    }

    public String RequestLambdaFunction(String question) throws Exception{
        HashMap<Object, Object> payloadMap = new HashMap<>();
        payloadMap.put("prompt", question);

        String payload = objectMapper.writeValueAsString(payloadMap);

        //lambda 함수 호출 요청
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("BedrockFunction")  // Lambda 함수 이름
                .payload(SdkBytes.fromUtf8String(payload)) // 입력 데이터 (질문) 설정
                .build();

        InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);

        String responsePayload = invokeResponse.payload().asUtf8String();

        return responsePayload;
    }

}
