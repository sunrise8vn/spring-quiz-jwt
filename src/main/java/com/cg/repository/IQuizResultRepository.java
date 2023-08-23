package com.cg.repository;

import com.cg.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IQuizResultRepository extends JpaRepository<QuizResult, Long> {
}
