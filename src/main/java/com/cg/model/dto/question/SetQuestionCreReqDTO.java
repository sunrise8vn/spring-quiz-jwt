package com.cg.model.dto.question;

import com.cg.model.Category;
import com.cg.model.Question;
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
public class SetQuestionCreReqDTO {

    private String questionContent;
    private Long categoryId;
    private List<AnswerCreReqDTO> answers;

    public Question toQuestion(Category category, EQuestionType questionType) {
        return new Question()
                .setId(null)
                .setContent(questionContent)
                .setCategory(category)
                .setType(questionType)
                ;
    }
}
