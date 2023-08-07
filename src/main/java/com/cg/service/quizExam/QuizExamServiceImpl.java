package com.cg.service.quizExam;


import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.QuizExam;
import com.cg.model.dto.quizExam.QuizExamCreReqDTO;
import com.cg.model.dto.quizExam.QuizExamDTO;
import com.cg.repository.ICategoryRepository;
import com.cg.repository.IQuizExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizExamServiceImpl implements IQuizExamService {

    @Autowired
    private IQuizExamRepository quizExamRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<QuizExam> findAll() {
        return quizExamRepository.findAll();
    }

    @Override
    public Optional<QuizExam> findById(Long id) {
        return quizExamRepository.findById(id);
    }

    @Override
    public List<QuizExamDTO> getAllQuizExamDTO() {
        return quizExamRepository.getAllQuizExamDTO();
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
