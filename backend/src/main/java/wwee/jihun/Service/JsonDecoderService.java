package wwee.jihun.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonDecoderService {
    private final ObjectMapper objectMapper;

    public  JsonDecoderService(){
        this.objectMapper = new ObjectMapper();
    }

    public String DecodeAndFormat(String Answer) throws Exception{
        JsonNode rootNode = objectMapper.readTree(Answer);
        String body = rootNode.get("body").asText();
        JsonNode bodyNode = objectMapper.readTree(body);

        return bodyNode.get("result").asText();
    }
}
