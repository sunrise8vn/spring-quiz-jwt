package com.cg.model.dto.question;

import com.cg.model.Answer;
import com.cg.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerCreReqDTO {

    private String content;

    private Boolean correct;

    public Answer toAnswer(Question question) {
        return new Answer()
                .setContent(content)
                .setCorrect(correct)
                .setQuestion(question)
                ;
    }
}
