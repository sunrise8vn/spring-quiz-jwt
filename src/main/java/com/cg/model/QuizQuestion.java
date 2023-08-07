package com.cg.model;


import com.cg.model.enums.EQuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quiz_questions")
@Accessors(chain = true)
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private Question question;

    @Column(name = "question_content", nullable = false)
    private String questionContent;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EQuestionType type;

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    private Quiz quiz;

    @Column(name = "right_answer")
    private Boolean rightAnswer;
}
