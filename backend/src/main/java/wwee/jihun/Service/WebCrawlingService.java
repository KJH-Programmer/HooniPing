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
    public String crawlByUserAndProduct(String userId, String product) throws IOException{
        List<CampaignEntity> campaigns = webCrawlingRepository.findAllByUserIdAndProduct(userId, product);
        if (campaigns.isEmpty()) {
            throw new IllegalArgumentException("해당하는 캠페인을 찾을 수 없습니다.");
        }
        // product 로 크롤링 URL 생성
        CampaignEntity campaign = campaigns.get(0);
        String crawlingURL = "https://www.google.com/search?q=" + campaign.getProduct() + " 광고&tbm=nws";
        // 크롤링 실행
        Document doc = Jsoup.connect(crawlingURL).get();
        return doc.toString();
    }
}
