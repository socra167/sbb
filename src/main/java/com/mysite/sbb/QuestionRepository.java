package com.mysite.sbb;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Optional<Question> findBySubject(String subject);

	Optional<Question> findBySubjectAndContent(String subject, String content);

	List<Question> findBySubjectLike(String subject);
}
