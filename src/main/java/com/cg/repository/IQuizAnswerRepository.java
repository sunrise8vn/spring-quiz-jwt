package com.cg.repository;


import com.cg.model.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
