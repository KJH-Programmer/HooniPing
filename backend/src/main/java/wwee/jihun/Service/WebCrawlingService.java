package wwee.jihun.Service;

import java.io.IOException;
import java.security.DrbgParameters;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Repository.WebCrawlingRepository;

@Service
public class WebCrawlingService {
    private final WebCrawlingRepository webCrawlingRepository;

    @Autowired
    public WebCrawlingService(WebCrawlingRepository webCrawlingRepository) {
        this.webCrawlingRepository = webCrawlingRepository;
    }
    // userId 와 Product 조회 후 웹크롤링 기능 추가
    public String crawlByUserAndProduct(String url){

        // 크롤링 실행
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
