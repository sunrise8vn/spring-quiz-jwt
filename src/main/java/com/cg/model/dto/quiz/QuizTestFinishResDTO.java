package com.cg.model.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizTestFinishResDTO {
    private Long currentCorrect;
    private Long numberQuestion;
    private BigDecimal currentScore;
    private BigDecimal score;
}
