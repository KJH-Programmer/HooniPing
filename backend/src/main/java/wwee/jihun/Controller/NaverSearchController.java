package wwee.jihun.Controller;

import org.springframework.web.bind.annotation.*;
import wwee.jihun.Controller.NaverSearchController;
import wwee.jihun.Service.NaverSearchService;

import java.util.Map;

@RestController
@RequestMapping("/api/naver-search")
@CrossOrigin(origins = {"http://localhost:8081"})
public class NaverSearchController {
    private final NaverSearchService naverSearchService;

    public NaverSearchController(NaverSearchService naverSearchService) {
        this.naverSearchService = naverSearchService;
    }

    @PostMapping
    public String searchNaver(@RequestBody Map<String, String> requestBody) {
        String product = requestBody.get("product");
        if (product == null || product.trim().isEmpty()) {
            return "Invalid product value";
        }
        return naverSearchService.searchProductAd(product);
    }
}
