package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;

	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("question", questionService.getQuestion(id));
		return "question_detail";
	}

	@GetMapping("/create")
	public String questionCreate() {
		return "question_form";
	}
}
