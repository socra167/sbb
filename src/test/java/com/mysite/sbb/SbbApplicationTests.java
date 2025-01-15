package com.mysite.sbb;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

		@BeforeEach
		void setUp() {
			questionRepository.deleteAll();
		}

		@Test
		@DisplayName("질문을 저장할 수 있다")
		void saveQuestion() {
			questionRepository.deleteAll();
			setUpQuestions();
		}

		private void setUpQuestions() {
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
		@DisplayName("저장된 질문을 모두 조회할 수 있다")
		void findAllQuesitons() {
			setUpQuestions();
			var allQuestions = questionRepository.findAll();
			assertThat(allQuestions).hasSize(2);
			var q = allQuestions.getFirst();
			assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
		}

		@Test
		@DisplayName("subject로 질문을 조회할 수 있다")
		void findBySubject() {
			setUpQuestions();
			var question = questionRepository.findBySubject("sbb가 무엇인가요?");
			assertThat(question).isPresent();
		}

		@Test
		@DisplayName("subject와 content로 질문을 조회할 수 있다")
		void findBySubjectAndContent() {
			setUpQuestions();
			var question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
			assertThat(question).isPresent();
		}

		@Test
		@DisplayName("subject의 특정 문자열로 질문을 검색할 수 있다")
		void findBySubjectLike() {
			setUpQuestions();
			var questions = questionRepository.findBySubjectLike("sbb%");
			var question = questions.getFirst();
			assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
		}
	}
}
