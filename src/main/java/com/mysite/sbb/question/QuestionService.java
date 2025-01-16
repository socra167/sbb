package com.mysite.sbb.question;

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
}
