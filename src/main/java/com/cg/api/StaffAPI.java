package com.cg.api;

import com.cg.model.dto.quizExam.QuizExamCreReqDTO;
import com.cg.service.quizExam.IQuizExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/staffs")
public class StaffAPI {

    @Autowired
    private IQuizExamService quizExamService;


    @PostMapping("/create-quiz-exam")
    public ResponseEntity<?> createQuizExam(@RequestBody QuizExamCreReqDTO quizExamCreReqDTO) {

        quizExamService.create(quizExamCreReqDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
