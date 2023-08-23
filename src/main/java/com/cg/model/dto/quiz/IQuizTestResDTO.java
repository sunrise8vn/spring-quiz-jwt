package com.cg.model.dto.quiz;

import java.util.Date;

public interface IQuizTestResDTO {

    Long getQuizQuestionId();
    Long getQuestionId();
    Long getNumberQuestion();
    Long getMinutes();
    Date getStartedOn();
    String getQuestionContent();
    String getQuestionType();
}
