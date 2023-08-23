package com.cg.service.quizAnswer;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.student.QuizAnswerCreDTO;
import com.cg.model.dto.student.StudentAnswerReqDTO;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.IQuizAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class QuizAnswerServiceImpl implements IQuizAnswerService {

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public List<QuizAnswer> findAll() {
        return null;
    }

    @Override
    public Optional<QuizAnswer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<QuizAnswer> findByQuizQuestionAndStudentAndDone(QuizQuestion quizQuestion, Student student, Boolean done) {
        return quizAnswerRepository.findByQuizQuestionAndStudentAndDone(quizQuestion, student, done);
    }

    @Override
    public void create(Student student, Quiz quiz, QuizQuestion quizQuestion, StudentAnswerReqDTO studentAnswerReqDTO) {

        for (QuizAnswerCreDTO item : studentAnswerReqDTO.getAnswers()) {
            answerRepository.findById(item.getId()).orElseThrow(() -> {
                throw new DataInputException("Thông tin câu trả lời không hợp lệ");
            });
        }

        String answerData = studentAnswerReqDTO.getAnswers().stream().map(Object::toString).collect(Collectors.joining(","));
        answerData = "[" + answerData + "]";

        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setStudent(student);
        quizAnswer.setQuiz(quiz);
        quizAnswer.setQuizQuestion(quizQuestion);
        quizAnswer.setAnswers(answerData);
        quizAnswer.setDone(false);
        quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public void update(QuizAnswer quizAnswer, StudentAnswerReqDTO studentAnswerReqDTO) {
        for (QuizAnswerCreDTO item : studentAnswerReqDTO.getAnswers()) {
            answerRepository.findById(item.getId()).orElseThrow(() -> {
                throw new DataInputException("Thông tin câu trả lời không hợp lệ");
            });
        }

        String answerData = studentAnswerReqDTO.getAnswers().stream().map(Object::toString).collect(Collectors.joining(","));
        answerData = "[" + answerData + "]";

        quizAnswer.setAnswers(answerData);
        quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public QuizAnswer save(QuizAnswer quizAnswer) {
        return quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public void delete(QuizAnswer quizAnswer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
