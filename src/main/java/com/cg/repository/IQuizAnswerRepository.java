package com.cg.repository;

import com.cg.model.QuizAnswer;
import com.cg.model.QuizQuestion;
import com.cg.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IQuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

    Optional<QuizAnswer> findByQuizQuestionAndStudentAndDone(QuizQuestion quizQuestion, Student student, Boolean done);


    @Modifying
    @Query(value = "CALL sp_insert_quiz_answer(:answers, :done, :quizId, :quizQuestionId, :studentId)", nativeQuery = true)
    void insertData(@Param("answers") String answers,
                    @Param("done") Boolean done,
                    @Param("quizId") Long quizId,
                    @Param("quizQuestionId") Long quizQuestionId,
                    @Param("studentId") Long studentId
    );
}
