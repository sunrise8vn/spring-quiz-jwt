package com.cg.service.quizQuestion;

import com.cg.model.*;
import com.cg.model.dto.quiz.IQuizQuestionAnsweredResDTO;
import com.cg.model.dto.quiz.IQuizTestResDTO;
import com.cg.model.dto.quiz.QuizTestAnswerResDTO;
import com.cg.model.dto.quiz.QuizTestResDTO;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.IQuizAnswerRepository;
import com.cg.repository.IQuizQuestionRepository;
import com.cg.service.quizAnswer.IQuizAnswerService;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class QuizQuestionServiceImpl implements IQuizQuestionService {

    @Autowired
    private IQuizQuestionRepository quizQuestionRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;

    @Override
    public List<QuizQuestion> findAll() {
        return null;
    }

    @Override
    public Optional<QuizQuestion> findById(Long id) {
        return quizQuestionRepository.findById(id);
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

//        List<Answer> answers = answerRepository.findAllByQuestion(question);

//        Map<Long, String> answerContents = new HashMap<>();
//
//        for (Answer item : answers) {
//            answerContents.put(item.getId(), item.getContent());
//        }

//        quizTestResDTO.setAnswers(answerContents);

        return quizTestResDTO;
    }

    @Override
    public QuizTestResDTO getQuizTestQuestion(Quiz quiz, Long offsetIndex) {
        IQuizTestResDTO iQuizTestResDTO = quizQuestionRepository.getQuizQuestionByQuizId(quiz.getId(), offsetIndex);

        QuizTestResDTO quizTestResDTO = new QuizTestResDTO();
        quizTestResDTO.setQuizId(quiz.getId());
        quizTestResDTO.setQuizExamId(quiz.getQuizExam().getId());
        quizTestResDTO.setQuizExamTitle(quiz.getQuizExam().getTitle());
        quizTestResDTO.setQuizQuestionId(iQuizTestResDTO.getQuizQuestionId());
        quizTestResDTO.setQuestionId(iQuizTestResDTO.getQuestionId());
        quizTestResDTO.setNumberQuestion(iQuizTestResDTO.getNumberQuestion());
        quizTestResDTO.setStartedOn(iQuizTestResDTO.getStartedOn());
        quizTestResDTO.setMinutes(iQuizTestResDTO.getMinutes());
        quizTestResDTO.setQuestionContent(iQuizTestResDTO.getQuestionContent());
        quizTestResDTO.setQuestionType(iQuizTestResDTO.getQuestionType());

//        List<IQuizQuestionAnsweredResDTO> questionAnswered = quizQuestionRepository.findAllIQuizQuestionAnswered(quiz.getId());
//        quizTestResDTO.setQuestionAnswered(questionAnswered);

//        Question question = new Question();
//        question.setId(quizTestResDTO.getQuestionId());
//
//        List<Answer> answers = answerRepository.findAllByQuestion(question);

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(iQuizTestResDTO.getQuizQuestionId());

        Optional<QuizAnswer> quizAnswer = quizAnswerRepository.findByQuizQuestionAndStudentAndDone(quizQuestion, quiz.getStudent(), false);

        List<QuizTestAnswerResDTO> quizTestAnswerResDTOS = new ArrayList<>();

        String answersData = quizAnswer.get().getAnswers();
        JSONArray answerDataArray = new JSONArray(answersData);

        for (int i = 0; i < answerDataArray.length(); i++) {
            JSONObject object = answerDataArray.getJSONObject(i);
            QuizTestAnswerResDTO quizTestAnswerResDTO = convertJSONtoQuizTestAnswerResDTO(object);
            quizTestAnswerResDTOS.add(quizTestAnswerResDTO);
        }

        quizTestResDTO.setAnswers(quizTestAnswerResDTOS);


//        if (quizAnswer.isEmpty()) {
//            List<QuizTestAnswerResDTO> quizTestAnswerResDTOS = new ArrayList<>();
//
//            for (Answer item : answers) {
//                QuizTestAnswerResDTO quizTestAnswerResDTO = new QuizTestAnswerResDTO();
//                quizTestAnswerResDTO.setId(item.getId());
//                quizTestAnswerResDTO.setContent(item.getContent());
//                quizTestAnswerResDTO.setChecked(false);
//
//                quizTestAnswerResDTOS.add(quizTestAnswerResDTO);
//            }
//
//            quizTestResDTO.setAnswers(quizTestAnswerResDTOS);
//        }
//        else {
//            String answersData = quizAnswer.get().getAnswers();
//            List<QuizTestAnswerResDTO> quizTestAnswerResDTOS = new ArrayList<>();
//
//            JSONArray answerDataArray = new JSONArray(answersData);
//            for (int i = 0; i < answerDataArray.length(); i++) {
//                JSONObject object = answerDataArray.getJSONObject(i);
//                QuizTestAnswerResDTO quizTestAnswerResDTO = convertJSONtoQuizTestAnswerResDTO(object);
//                String content = findAnswerContentById(answers, quizTestAnswerResDTO.getId());
//                quizTestAnswerResDTO.setContent(content);
//                quizTestAnswerResDTOS.add(quizTestAnswerResDTO);
//            }
//
//            quizTestResDTO.setAnswers(quizTestAnswerResDTOS);
//        }

        return quizTestResDTO;
    }

    public QuizTestAnswerResDTO convertJSONtoQuizTestAnswerResDTO(JSONObject object) throws JSONException {
        Long id = Long.parseLong(object.getString("id"));
        String content = object.getString("content");
        Boolean ans = Boolean.parseBoolean(object.getString("ans"));

        QuizTestAnswerResDTO quizTestAnswerResDTO = new QuizTestAnswerResDTO();
        quizTestAnswerResDTO.setId(id);
        quizTestAnswerResDTO.setContent(content);
        quizTestAnswerResDTO.setChecked(ans);

        return quizTestAnswerResDTO;
    }

    public String findAnswerContentById(List<Answer> answers, Long id) {
        for (Answer item : answers) {
            if (item.getId().equals(id)) {
                return item.getContent();
            }
        }

        return null;
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
