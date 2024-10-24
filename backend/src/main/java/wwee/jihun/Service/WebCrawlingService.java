package wwee.jihun.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class WebCrawlingService {

    // 고정된 URL로 웹 크롤링 기능 추가
    public String crawlAndExtractContent() throws IOException {
        // 고정된 URL
        String crawlingURL = "https://www.shinailbo.co.kr/news/articleView.html?idxno=1943254";  // 원하는 URL로 수정

        // 크롤링 실행
        Document doc = Jsoup.connect(crawlingURL).get();
        String htmlContent = doc.toString();  // 전체 HTML 가져오기

        // HTML 태그 제거
        String plainText = Jsoup.clean(htmlContent, Safelist.none());

        // 정규식을 사용해 첫 번째 "기자"부터 마지막 "기자"까지의 내용 추출
        Pattern pattern = Pattern.compile("(.*?기자)(.*기자)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(plainText);

        if (matcher.find()) {
            // 첫 번째 "기자"부터 마지막 "기자"까지의 내용 추출
            return matcher.group(0).trim();
        } else {
            return "기사를 찾을 수 없거나 형식이 맞지 않습니다.";
        }
    }
}
