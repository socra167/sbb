package com.mysite.sbb.question;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

	private final QuestionRepository questionRepository;

	public Page<Question> getList(int page) {
		Pageable pageable = PageRequest.of(page, 10);
		return questionRepository.findAll(pageable);
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
