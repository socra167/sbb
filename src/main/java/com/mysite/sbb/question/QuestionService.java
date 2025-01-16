package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public List<Question> getList() {
		return questionRepository.findAll();
	}

	public Question getQuestion(final Integer id) {
		return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));
	}

	public void create(String subject, String content) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreateDate(LocalDateTime.now());
		questionRepository.save(question);
	}
}
