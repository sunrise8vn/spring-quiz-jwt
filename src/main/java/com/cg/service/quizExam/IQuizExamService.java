package com.cg.service.quizExam;

import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.dto.quiz.QuizTestFinishResDTO;
import com.cg.model.dto.quizExam.QuizExamCreReqDTO;
import com.cg.model.dto.quizExam.QuizExamDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IQuizExamService extends IGeneralService<QuizExam, Long> {

    List<QuizExamDTO> getAllQuizExamDTO(Long studentId);

    void create(QuizExamCreReqDTO quizExamCreReqDTO);

    QuizTestFinishResDTO finish(Quiz quiz);
}
