package com.cg.repository;

import com.cg.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
