package com.cg.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizAnswerCreDTO {

    private Long id;
    private Boolean ans;

    @Override
    public String toString() {
        return "{" +
                "   'id': '" + id + '\'' +
                ",  'ans': '" + ans + '\'' +
                '}';
    }
}
