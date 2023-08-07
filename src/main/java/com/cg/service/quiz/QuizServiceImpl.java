package com.cg.service.quiz;

import com.cg.model.*;
import com.cg.model.dto.quizQuestion.IQuizQuestionDTO;
import com.cg.model.enums.EQuestionType;
import com.cg.repository.IQuizQuestionRepository;
import com.cg.repository.IQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuizServiceImpl implements IQuizService {

    @Autowired
    private IQuizRepository quizRepository;

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;


    @Override
    public List<Quiz> findAll() {
        return null;
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Quiz> findByQuizExamAndStudentAndDone(QuizExam quizExam, Student student, Boolean done) {
        return quizRepository.findByQuizExamAndStudentAndDone(quizExam, student, done);
    }

    @Override
    public Boolean existsByStudentAndDone(Student student, Boolean done) {
        return quizRepository.existsByStudentAndDone(student, done);
    }

    @Override
    public Quiz create(Student student, QuizExam quizExam) {
        Quiz quiz = new Quiz();
        quiz.setQuizExam(quizExam);
        quiz.setStudent(student);
        quiz.setScore(BigDecimal.ZERO);
        quiz.setDone(false);
        quizRepository.save(quiz);

        long numberQuestion = quizExam.getNumberQuestion();

        List<IQuizQuestionDTO> quizQuestionDTOS = quizQuestionRepository.getQuestionsRandom(numberQuestion);

        for (IQuizQuestionDTO item : quizQuestionDTOS) {
            Long questionId = item.getId();
            String questionContent = item.getContent();
            EQuestionType eQuestionType = EQuestionType.valueOf(item.getType());

            Question question = new Question();
            question.setId(questionId);

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setQuiz(quiz);
            quizQuestion.setQuestion(question);
            quizQuestion.setQuestionContent(questionContent);
            quizQuestion.setType(eQuestionType);
            quizQuestion.setRightAnswer(false);
            quizQuestionRepository.save(quizQuestion);
        }

        return quiz;
    }

    @Override
    public Quiz save(Quiz quiz) {
        return null;
    }

    @Override
    public void delete(Quiz quiz) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
