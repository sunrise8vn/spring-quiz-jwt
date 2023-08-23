package com.cg.model.dto.quizResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizResultResDTO {
    private Date startedOn;
    private Date completedOn;
    private Long countCorrect;
    private Long numberQuestion;
    private BigDecimal currentScore;
    private BigDecimal score;

    private List<QuizResultQuestionAnswerResDTO> questions;

}
