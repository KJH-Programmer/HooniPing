import openai
from swarm import Swarm, Agent
from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

# 환경 변수에서 OpenAI API 키 불러오기
openai.api_key = os.getenv('OPENAI_API_KEY')

client = Swarm()

# 각 에이전트 정의 (다른 에이전트의 결과를 결합하는 방식)
def combine_responses(a_response, b_response, c_response, d_response):
    return f"{a_response} {b_response} {c_response} {d_response}"

# A -> B -> C -> D 순으로 응답을 결합해 나감
agent_a = Agent(
    name="Agent A",
    instructions="https://v.daum.net/v/20240611182033559, http://www.hvacrj.co.kr/news/articleView.html?idxno=22629, https://zdnet.co.kr/view/?no=20240611110624, https://www.cstimes.com/news/articleView.html?idxno=596416 사이트에서 나오는 단어를 나열 해줘",
    functions=[],
)

agent_b = Agent(
    name="Agent B",
    instructions="위에 수십가지의 단어들 중 에어컨에 관한 단어만 뽑아줘.",
    functions=[],
)

agent_c = Agent(
    name="Agent C",
    instructions="그 단어들중에 에어컨 판매 광고에 쓰일만한 단어들만 100가지 골라줘",
    functions=[],
)

agent_d = Agent(
    name="Agent D",
    instructions="고른 100가지 단어중에 인기,조회수 높은걸로 30가지 골라줘",
    functions=[],
)

# 에이전트 A 실행
response_a = client.run(
    agent=agent_a,
    messages=[{"role": "user", "content": "."}],
)

# 에이전트 B 실행 (A의 응답을 B에 전달)
response_b = client.run(
    agent=agent_b,
    messages=[{"role": "user", "content": response_a.messages[-1]["content"]}],
)

# 에이전트 C 실행 (A와 B의 응답을 C에 전달)
response_c = client.run(
    agent=agent_c,
    messages=[{"role": "user", "content": response_b.messages[-1]["content"]}],
)

# 에이전트 D 실행 (A, B, C의 응답을 D에 전달)
response_d = client.run(
    agent=agent_d,
    messages=[{"role": "user", "content": response_c.messages[-1]["content"]}],
)

# 모든 에이전트의 응답을 합침
final_response = combine_responses(
    response_a.messages[-1]["content"],
    response_b.messages[-1]["content"],
    response_c.messages[-1]["content"],
    response_d.messages[-1]["content"]
)

# 최종 응답 출력
print(final_response)