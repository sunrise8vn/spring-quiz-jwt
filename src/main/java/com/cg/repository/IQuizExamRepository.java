package com.cg.repository;

import com.cg.model.QuizExam;
import com.cg.model.dto.quizExam.QuizExamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizExamRepository extends JpaRepository<QuizExam, Long> {


    @Query("SELECT NEW com.cg.model.dto.quizExam.QuizExamDTO (" +
                "qe.id, " +
                "qe.title, " +
                "qe.numberQuestion, " +
                "qu.quizExam.id" +
            ") " +
            "FROM QuizExam AS qe " +
            "LEFT JOIN Quiz AS qu " +
            "ON qu.quizExam = qe " +
            "AND qu.student.id = :studentId " +
            "AND qu.done = false "
    )
    List<QuizExamDTO> getAllQuizExamDTOForStudent(@Param("studentId") Long studentId);
}
