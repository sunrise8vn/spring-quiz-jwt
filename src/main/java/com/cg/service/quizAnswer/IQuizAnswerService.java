package com.cg.service.quizAnswer;

import com.cg.model.*;
import com.cg.model.dto.student.StudentAnswerReqDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IQuizAnswerService extends IGeneralService<QuizAnswer, Long> {

    Optional<QuizAnswer> findByQuizQuestionAndStudentAndDone(QuizQuestion quizQuestion, Student student, Boolean done);

    void create(Student student, Quiz quiz, QuizQuestion quizQuestion, StudentAnswerReqDTO studentAnswerReqDTO);

    void update(QuizAnswer quizAnswer, StudentAnswerReqDTO studentAnswerReqDTO);
}
