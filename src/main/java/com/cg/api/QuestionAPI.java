package com.cg.api;


import com.cg.model.dto.question.SetQuestionCreReqDTO;
import com.cg.service.question.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionAPI {

    @Autowired
    private IQuestionService questionService;


    @PostMapping("/add-new-set")
    public ResponseEntity<?> create(@RequestBody SetQuestionCreReqDTO questionCreReqDTO) {

        questionService.createSetQuestionWithAnswer(questionCreReqDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
