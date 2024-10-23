package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wwee.jihun.Service.WebCrawlingService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class WebCrawlingController {

    private final WebCrawlingService webCrawlingService;

    @Autowired
    public WebCrawlingController(WebCrawlingService webCrawlingService) {
        this.webCrawlingService = webCrawlingService;
    }
    // userId 와 Product 조회 후 웹크롤링 기능
    @GetMapping("/crawl")
    public String crawlByUserAndProduct(@RequestParam String userId, @RequestParam String product) {
        try {
            String result = webCrawlingService.crawlByUserAndProduct(userId, product);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "크롤링 실패: " + e.getMessage();
        }
    }
}
