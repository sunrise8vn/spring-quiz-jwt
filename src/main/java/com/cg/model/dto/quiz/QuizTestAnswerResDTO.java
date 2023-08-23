package com.cg.model.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizTestAnswerResDTO {
    private Long id;
    private String content;
    private Boolean checked;
}
