package com.cg.service.quizAnswer;

import com.cg.model.QuizAnswer;
import com.cg.repository.IQuizAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuizAnswerServiceImpl implements IQuizAnswerService {

    @Autowired
    private IQuizAnswerRepository quizAnswerRepository;

    @Override
    public List<QuizAnswer> findAll() {
        return null;
    }

    @Override
    public Optional<QuizAnswer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public QuizAnswer save(QuizAnswer quizAnswer) {
        return null;
    }

    @Override
    public void delete(QuizAnswer quizAnswer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
