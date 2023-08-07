package com.cg.service.question;

import com.cg.model.Question;
import com.cg.model.dto.question.SetQuestionCreReqDTO;
import com.cg.service.IGeneralService;

public interface IQuestionService extends IGeneralService<Question, Long> {

    void createSetQuestionWithAnswer(SetQuestionCreReqDTO questionCreReqDTO);
}
