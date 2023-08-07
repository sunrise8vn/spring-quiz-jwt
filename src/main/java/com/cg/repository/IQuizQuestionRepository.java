package com.cg.repository;


import com.cg.model.QuizQuestion;
import com.cg.model.dto.quiz.IQuizTestResDTO;
import com.cg.model.dto.quizQuestion.IQuizQuestionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    @Query(value = "CALL sp_get_questions_random(:numberQuestion)", nativeQuery = true)
    List<IQuizQuestionDTO> getQuestionsRandom(@Param("numberQuestion") Long numberQuestion);


    @Query(value = "CALL sp_get_quiz_question_by_quiz_id(:quizId, :offsetIndex)", nativeQuery = true)
    IQuizTestResDTO getQuizQuestionByQuizId(@Param("quizId") Long quizId, @Param("offsetIndex") Long offsetIndex);

//    @Query("SELECT NEW com.cg.model.dto.quiz.QuizTestResDTO (" +
//                "qq.id, " +
//                "qq.question.id, " +
//                "qq.quiz.quizExam.numberQuestion, " +
//                "qq.questionContent," +
//                "qq.type " +
//            ") " +
//            "FROM QuizQuestion AS qq " +
//            "WHERE qq.quiz.id = :quizId " +
//            "ORDER BY qq.id ASC "
//
//    )
//    QuizTestResDTO getQuizTestResDTO(@Param("quizId") Long quizId);
}
