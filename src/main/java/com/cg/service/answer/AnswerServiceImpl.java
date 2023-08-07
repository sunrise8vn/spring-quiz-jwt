package com.cg.service.answer;

import com.cg.model.Answer;
import com.cg.model.Question;
import com.cg.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private IAnswerRepository answerRepository;

    @Override
    public List<Answer> findAll() {
        return null;
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Answer> findAllByQuestion(Question question) {
        return answerRepository.findAllByQuestion(question);
    }

    @Override
    public Answer save(Answer answer) {
        return null;
    }

    @Override
    public void delete(Answer answer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
