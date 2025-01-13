package com.mysite.sbb;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
	}

	@Nested
	@DisplayName("데이터베이스 테스트")
	class databaseTest {

		@Test
		@Order(1)
		@DisplayName("질문을 저장할 수 있다.")
		void saveQuestion() {
			questionRepository.deleteAll();

			var q1 = new Question();
			q1.setSubject("sbb가 무엇인가요?");
			q1.setContent("sbb에 대해서 알고 싶습니다.");
			q1.setCreateDate(LocalDateTime.now());
			questionRepository.save(q1);

			var q2 = new Question();
			q2.setSubject("스프링부트 모델 질문입니다.");
			q2.setContent("id는 자동으로 생성되나요?");
			q2.setCreateDate(LocalDateTime.now());
			questionRepository.save(q2);
		}

		@Test
		@Order(2)
		@DisplayName("저장된 질문을 모두 조회할 수 있다.")
		void findAllQuesitons() {
			var allQuestions = questionRepository.findAll();
			assertThat(allQuestions.size()).isEqualTo(2);

			var q = allQuestions.get(0);
			assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
		}
	}
}
