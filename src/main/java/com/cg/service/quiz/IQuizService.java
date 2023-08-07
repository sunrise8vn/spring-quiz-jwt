package com.cg.service.quiz;

import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IQuizService extends IGeneralService<Quiz, Long> {

    Optional<Quiz> findByQuizExamAndStudentAndDone(QuizExam quizExam, Student student, Boolean done);

    Boolean existsByStudentAndDone(Student student, Boolean done);

    Quiz create(Student student, QuizExam quizExam);
}
