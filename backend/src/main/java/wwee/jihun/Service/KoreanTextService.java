package wwee.jihun.Service;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KoreanTextService {

    public List<String> extractKeywords(String text) {
        // 문장 정규화
        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);

        // 문장을 토큰화 (형태소 분석)
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

        // 명사, 형용사, 부사, 감탄사, 추출
        return OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens).stream()
                .filter(token -> token.toString().contains("Noun") || token.toString().contains("Adjective")
                        || token.toString().contains("Adverb") || token.toString().contains("Exclamation"))
                .map(KoreanTokenJava::getText)  // 텍스트 추출
                .distinct()
                .collect(Collectors.toList());
    }
}

