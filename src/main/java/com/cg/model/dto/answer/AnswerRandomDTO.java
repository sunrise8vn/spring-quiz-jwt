package com.cg.model.dto.answer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerRandomDTO {

    private Long id;
    private String content;
    private Boolean correct;
    private Boolean ans;

    public AnswerRandomDTO(Long id, String content, Boolean correct) {
        this.id = id;
        this.content = content;
        this.correct = correct;
        this.ans = false;
    }

    @Override
    public String toString() {
        return "{" +
                "   'id': '" + id + '\'' +
                ",  'content': '" + content + '\'' +
                ",  'correct': '" + correct + '\'' +
                ",  'ans': '" + ans + '\'' +
                '}';
    }
}
