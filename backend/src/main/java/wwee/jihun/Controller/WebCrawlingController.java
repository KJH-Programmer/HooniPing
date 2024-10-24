package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // URL로 크롤링 실행
    @GetMapping("/crawl")
    public String crawlAndExtractContent() {
        try {
            return webCrawlingService.crawlAndExtractContent();  // 메서드 이름 변경
        } catch (Exception e) {
            e.printStackTrace();
            return "크롤링 실패: " + e.getMessage();
        }
    }

}
