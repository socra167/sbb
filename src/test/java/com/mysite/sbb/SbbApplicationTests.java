package com.mysite.sbb;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	@Nested
	@DisplayName("데이터베이스 테스트")
	class databaseTest {

		@BeforeEach
		void setUp() {
			entityManager.createNativeQuery("""
				SET REFERENTIAL_INTEGRITY FALSE;
				TRUNCATE TABLE question RESTART IDENTITY;
				TRUNCATE TABLE answer RESTART IDENTITY;
				SET REFERENTIAL_INTEGRITY TRUE;
				"""
			).executeUpdate();
		}

		private void setUpExampleQuestions() {
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

		private void setUpExampleAnswers() {
			var foundQuestion = questionRepository.findById(2);
			assertThat(foundQuestion).isPresent();

			var question = foundQuestion.get();
			var answer = new Answer();
			answer.setContent("네 자동으로 생성됩니다.");
			answer.setCreateDate(LocalDateTime.now());
			answer.setQuestion(question);
			answerRepository.save(answer);
			question.addAnswer(answer);
		}

		@Nested
		@DisplayName("질문을")
		class questionTests {

			@Test
			@DisplayName("저장할 수 있다")
			void saveQuestion() {
				questionRepository.deleteAll();
				setUpExampleQuestions();
			}

			@Test
			@DisplayName("모두 조회할 수 있다")
			void findAllQuesitons() {
				setUpExampleQuestions();
				var allQuestions = questionRepository.findAll();
				assertThat(allQuestions).hasSize(2);
				var q = allQuestions.getFirst();
				assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
			}

			@Test
			@DisplayName("subject로 조회할 수 있다")
			void findBySubject() {
				setUpExampleQuestions();
				var question = questionRepository.findBySubject("sbb가 무엇인가요?");
				assertThat(question).isPresent();
			}

			@Test
			@DisplayName("subject와 content로 조회할 수 있다")
			void findBySubjectAndContent() {
				setUpExampleQuestions();
				var question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
				assertThat(question).isPresent();
			}

			@Test
			@DisplayName("subject의 특정 문자열로 검색할 수 있다")
			void findBySubjectLike() {
				setUpExampleQuestions();
				var questions = questionRepository.findBySubjectLike("sbb%");
				var question = questions.getFirst();
				assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요?");
			}

			@Test
			@DisplayName("수정할 수 있다")
			void updateQuestion() {
				setUpExampleQuestions();
				var foundQuestion = questionRepository.findById(1);
				assertThat(foundQuestion).isPresent();

				var question = foundQuestion.get();
				question.setSubject("수정된 제목");
				questionRepository.save(question);
			}

			@Test
			@DisplayName("삭제할 수 있다")
			void deleteQuestion() {
				setUpExampleQuestions();
				var foundQuestion = questionRepository.findById(1);
				assertThat(foundQuestion).isPresent();

				var question = foundQuestion.get();
				questionRepository.delete(question);
				assertThat(questionRepository.count()).isEqualTo(1);
			}

			@Test
			@DisplayName("질문으로 답변을 찾을 수 있다")
			void findAnswerByQuestion() {
				setUpExampleQuestions();
				setUpExampleAnswers();
				var foundQuestion = questionRepository.findById(2);
				assertThat(foundQuestion).isPresent();
				var question = foundQuestion.get();
				var answers = question.getAnswers();

				assertThat(answers).hasSize(1);
				assertThat(answers.getFirst().getContent()).isEqualTo("네 자동으로 생성됩니다.");
			}
		}

		@Nested
		@DisplayName("답변을")
		class answerTests {

			@Test
			@DisplayName("저장할 수 있다")
			void saveAnswer() {
				setUpExampleQuestions();
				setUpExampleAnswers();
			}

			@Test
			@DisplayName("조회할 수 있다")
			void findAnswer() {
				setUpExampleQuestions();
				setUpExampleAnswers();
				var foundAnswer = answerRepository.findById(1);
				assertThat(foundAnswer).isPresent();

				var answer = foundAnswer.get();
				assertThat(answer.getQuestion().getId()).isEqualTo(2);
			}
		}
	}
}
