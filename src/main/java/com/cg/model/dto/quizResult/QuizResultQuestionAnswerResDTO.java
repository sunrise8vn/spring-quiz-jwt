package com.cg.model.dto.quizResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizResultQuestionAnswerResDTO {
    private Long quizQuestionId;
    private Long questionId;
    private String questionContent;
    private String questionType;
    private Boolean correct;

    private List<QuizResultAnswerResDTO> answers;
}
