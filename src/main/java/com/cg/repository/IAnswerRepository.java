package com.cg.repository;

import com.cg.model.Answer;
import com.cg.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long> {


    List<Answer> findAllByQuestion(Question question);
}
