package com.cg.service.question;

import com.cg.exception.DataInputException;
import com.cg.model.Answer;
import com.cg.model.Category;
import com.cg.model.Question;
import com.cg.model.dto.question.AnswerCreReqDTO;
import com.cg.model.dto.question.SetQuestionCreReqDTO;
import com.cg.model.enums.EQuestionType;
import com.cg.repository.IAnswerRepository;
import com.cg.repository.ICategoryRepository;
import com.cg.repository.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private IQuestionRepository questionRepository;

    @Autowired
    private IAnswerRepository answerRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public Optional<Question> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void createSetQuestionWithAnswer(SetQuestionCreReqDTO questionCreReqDTO) {

        Category category = categoryRepository.findById(questionCreReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục câu hỏi không tồn tại");
        });

        Question question = questionCreReqDTO.toQuestion(category, EQuestionType.SINGLE);
        questionRepository.save(question);

        long countCorrect = 0;
        long countAnswer = 0;

        List<AnswerCreReqDTO> answerCreReqDTOS = questionCreReqDTO.getAnswers();
        List<Answer> answers = new ArrayList<>();

        for (AnswerCreReqDTO item : answerCreReqDTOS) {
            Answer answer = item.toAnswer(question);
            answers.add(answer);
            if (item.getCorrect()) {
                countCorrect++;
            }
            countAnswer++;
        }

        if (countAnswer < 2) {
            throw new DataInputException("Mỗi câu hỏi phải có ít nhất 2 câu trả lời, vui lòng kiểm tra lại thông tin");
        }
        else {
            if (countCorrect == 0) {
                throw new DataInputException("Không có câu trả lời đúng cho câu hỏi này, vui lòng kiểm tra lại thông tin");
            }
            else {
                if (countAnswer == 2) {
                    if (countCorrect == 2) {
                        throw new DataInputException("Câu hỏi đúng/sai chỉ có 1 câu trả lời đúng, vui lòng kiểm tra lại thông tin");
                    }
                    else {
                        question.setType(EQuestionType.TRUE_FALSE);
                        questionRepository.save(question);
                    }
                }
                else {
                    if (countCorrect > 1) {
                        question.setType(EQuestionType.MULTIPLE);
                        questionRepository.save(question);
                    }
                }
            }
        }

        answerRepository.saveAll(answers);
    }

    @Override
    public Question save(Question question) {
        return null;
    }

    @Override
    public void delete(Question question) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
