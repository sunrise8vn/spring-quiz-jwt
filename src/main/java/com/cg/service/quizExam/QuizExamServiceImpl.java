package com.cg.service.quizExam;


import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.quiz.QuizTestAnswerResDTO;
import com.cg.model.dto.quiz.QuizTestFinishResDTO;
import com.cg.model.dto.quizExam.QuizExamCreReqDTO;
import com.cg.model.dto.quizExam.QuizExamDTO;
import com.cg.repository.*;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizExamServiceImpl implements IQuizExamService {

    @Autowired
    private IQuizRepository quizRepository;

    @Autowired
    private IQuizExamRepository quizExamRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;

    @Autowired
    private IQuizResultRepository quizResultRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public List<QuizExam> findAll() {
        return quizExamRepository.findAll();
    }

    @Override
    public Optional<QuizExam> findById(Long id) {
        return quizExamRepository.findById(id);
    }

    @Override
    public List<QuizExamDTO> getAllQuizExamDTO(Long studentId) {
        return quizExamRepository.getAllQuizExamDTOForStudent(studentId);
    }

    @Override
    public void create(QuizExamCreReqDTO quizExamCreReqDTO) {
        Category category = categoryRepository.findById(quizExamCreReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục câu hỏi không tồn tại");
        });

        QuizExam quizExam = quizExamCreReqDTO.toQuizExam(category);
        quizExamRepository.save(quizExam);
    }

    @Override
    public QuizTestFinishResDTO finish(Quiz quiz) {
        QuizTestFinishResDTO quizTestFinishResDTO = new QuizTestFinishResDTO();
        QuizExam quizExam = quiz.getQuizExam();

        BigDecimal examScore = quizExam.getScore();
        long numberQuestion = quizExam.getNumberQuestion();
        BigDecimal currentScore = BigDecimal.ZERO;
        long numberCorrect = 0;

        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAllByQuiz(quiz);

        for (QuizQuestion quizQuestion : quizQuestions) {
            boolean correct = true;

            Optional<QuizAnswer> quizAnswerOptional = quizAnswerRepository.findByQuizQuestionAndStudentAndDone(quizQuestion, quiz.getStudent(), false);

            if (quizAnswerOptional.isEmpty()) {
                correct = false;
            }
            else {
                QuizAnswer quizAnswer = quizAnswerOptional.get();
                String answersData = quizAnswer.getAnswers();

                Question question = quizQuestion.getQuestion();
                List<Answer> answers = answerRepository.findAllByQuestion(question);

                for (Answer answer : answers) {
                    JSONArray answerDataArray = new JSONArray(answersData);
                    for (int i = 0; i < answerDataArray.length(); i++) {
                        JSONObject object = answerDataArray.getJSONObject(i);
                        QuizTestAnswerResDTO quizTestAnswerResDTO = convertJSONtoQuizTestAnswerResDTO(object);

                        if (answer.getId().equals(quizTestAnswerResDTO.getId())) {
                            if (answer.getCorrect() != quizTestAnswerResDTO.getChecked()) {
                                correct = false;
                            }
                        }
                    }
                }

                if (correct) {
                    numberCorrect++;
                }
            }
        }

        currentScore = examScore.divide(BigDecimal.valueOf(numberQuestion)).multiply(BigDecimal.valueOf(numberCorrect));

        quizTestFinishResDTO.setCurrentCorrect(numberCorrect);
        quizTestFinishResDTO.setNumberQuestion(quizExam.getNumberQuestion());
        quizTestFinishResDTO.setScore(quizExam.getScore());
        quizTestFinishResDTO.setCurrentScore(currentScore);

        quiz.setNumberCorrect(numberCorrect);
        quiz.setScore(currentScore);
        quiz.setDone(true);
        quizRepository.save(quiz);

        return quizTestFinishResDTO;

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
    public QuizExam save(QuizExam quizExam) {
        return quizExamRepository.save(quizExam);
    }

    @Override
    public void delete(QuizExam quizExam) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
