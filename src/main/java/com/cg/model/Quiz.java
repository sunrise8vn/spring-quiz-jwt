package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quiz")
@Accessors(chain = true)
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_exam_id", referencedColumnName = "id", nullable = false)
    private QuizExam quizExam;

    @Column(name = "number_correct", nullable = false)
    private Long numberCorrect;

    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal score;

    @Column(columnDefinition = "boolean default false")
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;
}
