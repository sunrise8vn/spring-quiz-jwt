package com.cg.service.quiz;

import com.cg.model.*;
import com.cg.model.dto.answer.AnswerRandomDTO;
import com.cg.model.dto.quizQuestion.IQuizQuestionDTO;
import com.cg.model.enums.EQuestionType;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.IQuizAnswerRepository;
import com.cg.repository.IQuizQuestionRepository;
import com.cg.repository.IQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class QuizServiceImpl implements IQuizService {

    @Autowired
    private IQuizRepository quizRepository;

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;


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
    public Optional<Quiz> findIdAndByStudentAndDone(Long id, Student student, Boolean done) {
        return quizRepository.findByIdAndStudentAndDone(id, student, done);
    }

    @Override
    public Boolean existsByQuizExamAndStudentAndDone(QuizExam quizExam, Student student, Boolean done) {
        return quizRepository.existsByQuizExamAndStudentAndDone(quizExam, student, done);
    }

    @Override
    public Quiz create(Student student, QuizExam quizExam) {
        Quiz quiz = new Quiz();
        quiz.setQuizExam(quizExam);
        quiz.setStudent(student);
        quiz.setNumberCorrect(0L);
        quiz.setScore(BigDecimal.ZERO);
        quiz.setDone(false);
        quizRepository.save(quiz);

        long numberQuestion = quizExam.getNumberQuestion();

        List<IQuizQuestionDTO> quizQuestionDTOS = quizQuestionRepository.getQuestionsRandom(quizExam.getCategory().getId(), numberQuestion);

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

            List<AnswerRandomDTO> answerRandomDTOS = answerRepository.findAllRandomByQuestion(question);

            String answerData = answerRandomDTOS.stream().map(Object::toString).collect(Collectors.joining(","));
            answerData = "[" + answerData + "]";

//            QuizAnswer quizAnswer = new QuizAnswer();
//            quizAnswer.setQuiz(quiz);
//            quizAnswer.setQuizQuestion(quizQuestion);
//            quizAnswer.setStudent(student);
//            quizAnswer.setAnswers(answerData);
//            quizAnswer.setDone(false);
            quizAnswerRepository.insertData(answerData, false, quiz.getId(), quizQuestion.getId(), student.getId());

        }

        return quiz;
    }

    @Override
    public void finishDelay(Quiz quiz) {
        quiz.setDone(true);
        quizRepository.save(quiz);
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
