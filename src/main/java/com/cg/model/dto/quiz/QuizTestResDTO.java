package com.cg.model.dto.quiz;

import com.cg.model.enums.EQuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizTestResDTO {

    private Long quizExamId;
    private String quizExamTitle;
    private Long quizQuestionId;
    private Long questionId;
    private Long numberQuestion;
    private String questionContent;
    private String questionType;
    private List<String> answers;

    public QuizTestResDTO(Long quizExamId, Long quizQuestionId, Long questionId, Long numberQuestion, String questionContent, EQuestionType eQuestionType) {
        this.quizExamId = quizExamId;
        this.quizQuestionId = quizQuestionId;
        this.questionId = questionId;
        this.numberQuestion = numberQuestion;
        this.questionContent = questionContent;
        this.questionType = eQuestionType.getValue();
    }
}
