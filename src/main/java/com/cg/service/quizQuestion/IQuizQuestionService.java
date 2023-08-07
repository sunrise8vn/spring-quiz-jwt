package com.cg.service.quizQuestion;

import com.cg.model.Quiz;
import com.cg.model.QuizQuestion;
import com.cg.model.dto.quiz.QuizTestResDTO;
import com.cg.service.IGeneralService;

public interface IQuizQuestionService extends IGeneralService<QuizQuestion, Long> {

    QuizTestResDTO getQuizTestFirst(Quiz quiz);

    QuizTestResDTO getQuizTestQuestion(Quiz quiz, Long offsetIndex);
}
