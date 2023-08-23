package com.cg.model.dto.quizResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizResultAnswerResDTO {
    private Long id;
    private String answerContent;
    private Boolean ans;
    private Boolean correct;
}
