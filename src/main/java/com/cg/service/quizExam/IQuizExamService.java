package com.cg.service.quizExam;

import com.cg.model.Category;
import com.cg.model.QuizExam;
import com.cg.model.dto.quizExam.QuizExamCreReqDTO;
import com.cg.model.dto.quizExam.QuizExamDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IQuizExamService extends IGeneralService<QuizExam, Long> {

    List<QuizExamDTO> getAllQuizExamDTO();

    void create(QuizExamCreReqDTO quizExamCreReqDTO);
}
