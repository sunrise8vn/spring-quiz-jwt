package com.cg.repository;


import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByQuizExamAndStudentAndDone(QuizExam quizExam, Student student, Boolean done);

    Optional<Quiz> findByIdAndStudentAndDone(Long id, Student student, Boolean done);

    Boolean existsByStudentAndDone(Student student, Boolean done);

    Boolean existsByQuizExamAndStudentAndDone(QuizExam quizExam, Student student, Boolean done);
}
