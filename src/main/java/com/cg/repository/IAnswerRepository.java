package com.cg.repository;

import com.cg.model.Answer;
import com.cg.model.Question;
import com.cg.model.dto.answer.AnswerRandomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByQuestion(Question question);

    @Query("SELECT NEW com.cg.model.dto.answer.AnswerRandomDTO (" +
                "ans.id, " +
                "ans.content, " +
                "ans.correct " +
            ") " +
            "FROM Answer AS ans " +
            "WHERE ans.question = :question " +
            "ORDER BY RAND()"
    )
    List<AnswerRandomDTO> findAllRandomByQuestion(@Param("question") Question question);

}
