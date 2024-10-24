package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import wwee.jihun.Service.RagService;
import wwee.jihun.Service.WebCrawlingService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class WebCrawlingController {

    private final WebCrawlingService webCrawlingService;
    private final RagService ragService;

    @Autowired
    public WebCrawlingController(WebCrawlingService webCrawlingService, RagService ragService) {
        this.webCrawlingService = webCrawlingService;
        this.ragService = ragService;
    }

    //tavily search
    @GetMapping("/search")
    public Mono<List<String>> productSearch(@RequestParam String product){

        return ragService.callTavilyModel(product);
    }
}
