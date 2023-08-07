package com.cg.service.answer;

import com.cg.model.Answer;
import com.cg.model.Question;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IAnswerService extends IGeneralService<Answer, Long> {

    List<Answer> findAllByQuestion(Question question);
}
