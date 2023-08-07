package com.cg.repository;

import com.cg.model.QuizExam;
import com.cg.model.dto.quizExam.QuizExamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizExamRepository extends JpaRepository<QuizExam, Long> {


    @Query("SELECT NEW com.cg.model.dto.quizExam.QuizExamDTO (" +
                "qe.id, " +
                "qe.title, " +
                "qe.numberQuestion" +
            ") " +
            "FROM QuizExam AS qe"
    )
    List<QuizExamDTO> getAllQuizExamDTO();
}
