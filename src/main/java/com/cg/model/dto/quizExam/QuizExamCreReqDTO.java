package com.cg.model.dto.quizExam;

import com.cg.model.Category;
import com.cg.model.QuizExam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizExamCreReqDTO {

    private String title;
    private Long categoryId;
    private Long numberQuestion;

    public QuizExam toQuizExam(Category category) {
        return new QuizExam()
                .setTitle(title)
                .setCategory(category)
                .setNumberQuestion(numberQuestion)
                ;
    }

}
