package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import wwee.jihun.Service.TavilySearchService;

import java.util.List;

@RestController
@RequestMapping("/api/tavily")
@CrossOrigin(origins = {"http://localhost:8081"})
public class TavilyController {

    private final TavilySearchService tavilySearchService;

    // 생성자 주입을 통해 TavilySearchService 주입받음
    public TavilyController(TavilySearchService tavilySearchService) {
        this.tavilySearchService = tavilySearchService;
    }

    // /api/tavily 엔드포인트에 GET 요청을 처리
    @PostMapping
    public List<String> searchProduct(@RequestBody String product) {
        return tavilySearchService.searchProduct(product); // 서비스 메서드 호출
    }

}
