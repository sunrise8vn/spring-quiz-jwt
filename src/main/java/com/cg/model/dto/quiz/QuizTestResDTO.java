package com.cg.model.dto.quiz;

import com.cg.model.enums.EQuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizTestResDTO {

    private Long quizId;
    private Long quizExamId;
    private String quizExamTitle;
    private Long quizQuestionId;
    private Long questionId;
    private Long numberQuestion;
    private List<IQuizQuestionAnsweredResDTO> questionAnswered;
    private Date startedOn;
    private Long minutes;
    private String questionContent;
    private String questionType;
//    private Map<Long, String> answers;
    private List<QuizTestAnswerResDTO> answers;

    public QuizTestResDTO(Long quizId, Long quizExamId, Long quizQuestionId, Long questionId, Long numberQuestion, String questionContent, EQuestionType eQuestionType) {
        this.quizId = quizId;
        this.quizExamId = quizExamId;
        this.quizQuestionId = quizQuestionId;
        this.questionId = questionId;
        this.numberQuestion = numberQuestion;
        this.questionContent = questionContent;
        this.questionType = eQuestionType.getValue();
    }
}
