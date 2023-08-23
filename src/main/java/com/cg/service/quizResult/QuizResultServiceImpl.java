package com.cg.service.quizResult;


import com.cg.model.*;
import com.cg.model.dto.quiz.QuizTestAnswerResDTO;
import com.cg.model.dto.quizResult.QuizResultAnswerResDTO;
import com.cg.model.dto.quizResult.QuizResultQuestionAnswerResDTO;
import com.cg.model.dto.quizResult.QuizResultResDTO;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.IQuizAnswerRepository;
import com.cg.repository.IQuizQuestionRepository;
import com.cg.repository.IQuizResultRepository;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizResultServiceImpl implements IQuizResultService {

    @Autowired
    private IQuizResultRepository quizResultRepository;

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;

    @Autowired
    private IAnswerRepository answerRepository;


    @Override
    public List<QuizResult> findAll() {
        return quizResultRepository.findAll();
    }

    @Override
    public Optional<QuizResult> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public QuizResultResDTO getQuizResultResDTO(Quiz quiz) {
        QuizResultResDTO quizResultResDTO = new QuizResultResDTO();
        List<QuizResultQuestionAnswerResDTO> questions = new ArrayList<>();

        QuizExam quizExam = quiz.getQuizExam();

        BigDecimal examScore = quizExam.getScore();
        long numberQuestion = quizExam.getNumberQuestion();
        BigDecimal currentScore = BigDecimal.ZERO;
        long numberCorrect = 0;

        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAllByQuiz(quiz);

        for (QuizQuestion quizQuestion : quizQuestions) {
            QuizResultQuestionAnswerResDTO quizResultQuestionAnswerResDTO = new QuizResultQuestionAnswerResDTO();
            quizResultQuestionAnswerResDTO.setQuestionId(quizQuestion.getQuestion().getId());
            quizResultQuestionAnswerResDTO.setQuizQuestionId(quizQuestion.getId());
            quizResultQuestionAnswerResDTO.setQuestionContent(quizQuestion.getQuestionContent());
            quizResultQuestionAnswerResDTO.setQuestionType(quizQuestion.getType().getValue());

            List<QuizResultAnswerResDTO> quizResultAnswerResDTOS = new ArrayList<>();

            boolean correct = true;

            Optional<QuizAnswer> quizAnswerOptional = quizAnswerRepository.findByQuizQuestionAndStudentAndDone(quizQuestion, quiz.getStudent(), false);

            QuizAnswer quizAnswer = quizAnswerOptional.get();
            String answersData = quizAnswer.getAnswers();

            Question question = quizQuestion.getQuestion();
            List<Answer> answers = answerRepository.findAllByQuestion(question);

            for (Answer answer : answers) {
                JSONArray answerDataArray = new JSONArray(answersData);

                QuizResultAnswerResDTO quizResultAnswerResDTO = new QuizResultAnswerResDTO();
                quizResultAnswerResDTO.setId(answer.getId());
                quizResultAnswerResDTO.setCorrect(answer.getCorrect());
                quizResultAnswerResDTO.setAnswerContent(answer.getContent());

                for (int i = 0; i < answerDataArray.length(); i++) {
                    JSONObject object = answerDataArray.getJSONObject(i);
                    QuizTestAnswerResDTO quizTestAnswerResDTO = convertJSONtoQuizTestAnswerResDTO(object);

                    if (answer.getId().equals(quizTestAnswerResDTO.getId())) {
                        quizResultAnswerResDTO.setAns(quizTestAnswerResDTO.getChecked());

                        if (answer.getCorrect() != quizTestAnswerResDTO.getChecked()) {
                            correct = false;
                        }
                    }
                }

                quizResultAnswerResDTOS.add(quizResultAnswerResDTO);
            }

            if (correct) {
                numberCorrect++;
            }

            quizResultQuestionAnswerResDTO.setCorrect(correct);
            quizResultQuestionAnswerResDTO.setAnswers(quizResultAnswerResDTOS);
            questions.add(quizResultQuestionAnswerResDTO);
        }

        quizResultResDTO.setStartedOn(quiz.getCreatedAt());
        quizResultResDTO.setCompletedOn(quiz.getUpdatedAt());

        currentScore = examScore.divide(BigDecimal.valueOf(numberQuestion)).multiply(BigDecimal.valueOf(numberCorrect));

        quizResultResDTO.setCountCorrect(numberCorrect);
        quizResultResDTO.setNumberQuestion(quizExam.getNumberQuestion());
        quizResultResDTO.setScore(quizExam.getScore());
        quizResultResDTO.setCurrentScore(currentScore);

        quizResultResDTO.setQuestions(questions);

        return quizResultResDTO;
    }

    public QuizTestAnswerResDTO convertJSONtoQuizTestAnswerResDTO(JSONObject object) throws JSONException {
        Long id = Long.parseLong(object.getString("id"));
        Boolean ans = Boolean.parseBoolean(object.getString("ans"));

        QuizTestAnswerResDTO quizTestAnswerResDTO = new QuizTestAnswerResDTO();
        quizTestAnswerResDTO.setId(id);
        quizTestAnswerResDTO.setChecked(ans);
        return quizTestAnswerResDTO;
    }

    @Override
    public QuizResult save(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    @Override
    public void delete(QuizResult quizResult) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
