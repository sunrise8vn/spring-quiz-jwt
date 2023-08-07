package com.cg.model.dto.quizExam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizExamDTO {

    private Long id;
    private String title;
    private Long numberQuestion;

}
