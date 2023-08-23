package com.cg.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentAnswerReqDTO {
//    Map<Long, Boolean> answers;

    List<QuizAnswerCreDTO> answers;


}
