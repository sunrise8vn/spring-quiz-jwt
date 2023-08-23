package com.cg.service.quizResult;

import com.cg.model.Quiz;
import com.cg.model.QuizResult;
import com.cg.model.dto.quizResult.QuizResultResDTO;
import com.cg.service.IGeneralService;

public interface IQuizResultService extends IGeneralService<QuizResult, Long> {

    QuizResultResDTO getQuizResultResDTO(Quiz quiz);
}
