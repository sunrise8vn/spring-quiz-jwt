package com.cg.service.quizQuestion;

import com.cg.model.Answer;
import com.cg.model.Question;
import com.cg.model.Quiz;
import com.cg.model.QuizQuestion;
import com.cg.model.dto.quiz.IQuizTestResDTO;
import com.cg.model.dto.quiz.QuizTestResDTO;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.IQuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuizQuestionServiceImpl implements IQuizQuestionService {

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public List<QuizQuestion> findAll() {
        return null;
    }

    @Override
    public Optional<QuizQuestion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public QuizTestResDTO getQuizTestFirst(Quiz quiz) {

//        QuizTestResDTO quizTestResDTO = quizQuestionRepository.getQuizTestResDTO(quiz.getId());

        IQuizTestResDTO iQuizTestResDTO = quizQuestionRepository.getQuizQuestionByQuizId(quiz.getId(), 0L);

        QuizTestResDTO quizTestResDTO = new QuizTestResDTO();
        quizTestResDTO.setQuizQuestionId(iQuizTestResDTO.getQuizQuestionId());
        quizTestResDTO.setQuestionId(iQuizTestResDTO.getQuestionId());
        quizTestResDTO.setNumberQuestion(iQuizTestResDTO.getNumberQuestion());
        quizTestResDTO.setQuestionContent(iQuizTestResDTO.getQuestionContent());
        quizTestResDTO.setQuestionType(iQuizTestResDTO.getQuestionType());

        Question question = new Question();
        question.setId(quizTestResDTO.getQuestionId());

        List<Answer> answers = answerRepository.findAllByQuestion(question);

        List<String> answerContents = new ArrayList<>();

        for (Answer item : answers) {
            answerContents.add(item.getContent());
        }

        quizTestResDTO.setAnswers(answerContents);

        return quizTestResDTO;
    }

    @Override
    public QuizTestResDTO getQuizTestQuestion(Quiz quiz, Long offsetIndex) {
        IQuizTestResDTO iQuizTestResDTO = quizQuestionRepository.getQuizQuestionByQuizId(quiz.getId(), offsetIndex);

        QuizTestResDTO quizTestResDTO = new QuizTestResDTO();
        quizTestResDTO.setQuizExamId(quiz.getQuizExam().getId());
        quizTestResDTO.setQuizExamTitle(quiz.getQuizExam().getTitle());
        quizTestResDTO.setQuizQuestionId(iQuizTestResDTO.getQuizQuestionId());
        quizTestResDTO.setQuestionId(iQuizTestResDTO.getQuestionId());
        quizTestResDTO.setNumberQuestion(iQuizTestResDTO.getNumberQuestion());
        quizTestResDTO.setQuestionContent(iQuizTestResDTO.getQuestionContent());
        quizTestResDTO.setQuestionType(iQuizTestResDTO.getQuestionType());

        Question question = new Question();
        question.setId(quizTestResDTO.getQuestionId());

        List<Answer> answers = answerRepository.findAllByQuestion(question);

        List<String> answerContents = new ArrayList<>();

        for (Answer item : answers) {
            answerContents.add(item.getContent());
        }

        quizTestResDTO.setAnswers(answerContents);

        return quizTestResDTO;
    }

    @Override
    public QuizQuestion save(QuizQuestion quizQuestion) {
        return null;
    }

    @Override
    public void delete(QuizQuestion quizQuestion) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
