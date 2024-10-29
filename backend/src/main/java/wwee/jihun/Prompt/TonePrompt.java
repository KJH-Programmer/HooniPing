package wwee.jihun.Prompt;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TonePrompt {

    private String Luckviki = "긍정적인 사고로,모든 사건이 궁극적으로 좋은 결과를 가져온다고 믿는 태도.\n" +
            "- 원영적 사고:부정적인 감정을 거부하는 '해로운 긍정성'과 다르며,현실을 인지한 후 긍정적으로 해석.\n" +
            "- 예시:\n" +
            "  - 지하철을 놓쳤을 때:\"내가 지하철을 놓쳤지만,기다리며 좋은 시간을 가질 수 있어! 완전 럭키비키잖앙.\"\n" +
            "  - 프로젝트 실패:\"실패 덕분에 배울 수 있었어! 완전 럭키비키잖앙.\"\n" +
            "  - 비로 소풍 취소:\"집에서 가족과 특별한 시간을 가질 수 있어서 좋아! 완전 럭키비키잖앙.\"\n";

    private String haeyo = "듣는 이를 높이는 한국어의 두루높임 비격식체.편안하고 부드러운 존댓말로 일상에서 자주 사용됨.\n" +
            "- 다양한 높임법과 함께 사용 가능하며,'-요' 형태로 이루어진 어미가 특징.비교적 자유롭게 사용 가능한 존댓말로 '두루'높임이라 불림.\n" +
            "- 현대 구어에서 많이 쓰이며 편안함을 주고,높임을 더할 때 하십시오체와 섞어 사용 가능.\n" +
            "- 예시: '같아요', '바라요'는 해요체에 속함.과거에는 하오체와 하게체를 대체하며 구어에서 더욱 주로 사용됨.";

    private String seubnida = "아주 높은 격식체,하십시오체로 상대를 가장 높이는 한국어의 아주높임 격식체.\n" +
            "- '-습니다'형태로 표현하며,높임 정도가 매우 높아 주로 공식적인 자리,문서에서 사용됨.\n" +
            "- 문어에서 보편적으로 쓰이며 해요체와 섞어 사용해 매끄러움을 높일 수도 있음.단독 사용 시 딱딱한 느낌을 주며 비공식적 공간에서는 잘 사용되지 않음.\n" +
            "- 면접,발표,안내문,문제집 등에서 주로 쓰임.\n" +
            "- 예시: '이게 맞습니다', '들어가겠습니다' 등이 있으며,대화에서 하십시오체만 사용 시 해요체를 빌려 매끄럽게 쓰기도 함.";

    private String banmal = "친밀한 관계에서 사용하는 말로,높이지도 낮추지도 않는 비공식적인 대화체.\n" +
            "- 상대가 자신보다 어리거나 친할 때 주로 사용하며 높임 없이 자유로움을 표현함.\n" +
            "- 매우 친한 경우 나이나 지위가 많더라도 반말을 쓰며,어감은 해체와 비슷함.상대의 동의를 구하거나 친해진 후 사용하는 것이 예의로 권장됨.\n" +
            "- 예시: '이 책 재미있어?', '아주 재미있는걸'등의 표현이 있으며,낮춤 말투로는 해체,해라체 등을 포괄함.";

    private String neologism = "국민정치인: 존경받는 정치인.\n" +
            "가즈아: 긍정적인 기대.\n" +
            "국뽕: 국가에 대한 자신감.\n" +
            "급식체: 10대 청소년들의 줄임말과 초성체 사용.\n" +
            "뉴트로: 고전을 현대적으로 해석한 유행.\n" +
            "딸바보: 딸을 사랑하는 사람.\n" +
            "뚜따: CPU 히트 스프레더 제거.\n" +
            "라떼는 말이야: 기성세대의 발언을 비꼬는 표현.\n" +
            "레게노: '레전드' 오타에서 유래.\n" +
            "몸짱: 몸매가 좋은 사람.\n" +
            "무슨 129: 경상도 사투리 변형.\n" +
            "불금: '불타는 금요일'.\n" +
            "벼락거지: 자산 격차로 인한 상대적 빈곤.\n" +
            "빵셔틀: 심부름 학생.\n" +
            "시발비용: 홧김에 소비하는 비용.\n" +
            "아무 말 대잔치: 맥락 없는 말.\n" +
            "어쩔티비: '어쩌라고'의 변형.\n" +
            "의문의 1승: 의도하지 않았지만 이득 본 상황.\n" +
            "일진: 불량 학생.\n" +
            "지름신: 충동 구매 욕구.\n" +
            "진지충: 상황과 맞지 않게 진지한 사람.\n" +
            "창렬: 품질이 낮은 것을 비꼬는 표현.\n" +
            "치느님: 한국 치킨 찬양.\n" +
            "캐-: '개'의 격음화.\n" +
            "탕진잼: 소액 소비의 재미.\n" +
            "핵-: 강함을 강조.\n" +
            "홍대병: 한국형 힙스터 비꼬기.\n" +
            "흙수저: 경제적 어려움을 겪는 사람.\n" +
            "가성비: 가격 대비 성능.\n" +
            "가심비: 가격 대비 심리적 만족.\n" +
            "갑분싸: 분위기 싸해짐.\n" +
            "남사친: 남자 사람 친구.\n" +
            "내로남불: 이중잣대.\n" +
            "단짠: 단맛과 짠맛.\n" +
            "ㄹㅇㅋㅋ: '레알ㅋㅋ'.\n" +
            "먹튀: 책임지지 않는 행위.\n" +
            "밀당: 연인 사이 심리전.\n" +
            "브금: BGM.\n" +
            "사바사: '사람 by 사람', 상황 따라 다름.\n" +
            "성진국: 성문화 발달국.\n" +
            "썸: 교제 전 감정.\n" +
            "악플: 나쁜 댓글.\n" +
            "여사친: 여자 사람 친구.\n" +
            "열폭: 열등감으로 비방.\n" +
            "움짤: 움직이는 짤방.\n" +
            "웃프다: 웃기지만 슬픈 상황.\n" +
            "자라니: 자전거 타는 사람 비하.\n" +
            "지못미: 지켜주지 못해 미안.\n" +
            "치맥: 치킨과 맥주.\n" +
            "텅장: 텅 빈 통장.\n" +
            "흠좀무: 믿기 어려운 이야기.";
}
