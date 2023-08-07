package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.exception.UnauthorizedException;
import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.model.dto.quiz.QuizTestResDTO;
import com.cg.model.dto.quizExam.QuizExamDTO;
import com.cg.service.question.IQuestionService;
import com.cg.service.quiz.IQuizService;
import com.cg.service.quizExam.IQuizExamService;
import com.cg.service.quizQuestion.IQuizQuestionService;
import com.cg.service.student.IStudentService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IQuizExamService quizExamService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IQuizQuestionService quizQuestionService;

    @Autowired
    private IQuizService quizService;

    @GetMapping("/get-all-quiz-exam")
    public ResponseEntity<?> getALlQuizExam() {

        List<QuizExamDTO> quizExams = quizExamService.getAllQuizExamDTO();

        return new ResponseEntity<>(quizExams, HttpStatus.OK);
    }


    @PostMapping("/new-quiz")
    public ResponseEntity<?> createNewQuiz(HttpServletRequest request) throws IOException {

        String username = appUtils.getPrincipalUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UnauthorizedException("Vui lòng đăng nhập để sử dụng dịch vụ");
        });

        Student student = studentService.findByUser(user).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        ObjectMapper mapper = new JsonMapper();
        JsonNode json = mapper.readTree(body);

        String quizExamIdStr;

        try {
            quizExamIdStr = json.get("quizExamId").asText();
        } catch (Exception e) {
            throw new DataInputException("Dữ liệu không hợp lệ, vui lòng kiểm tra lại thông tin");
        }

        if (!validateUtils.isNumberValid(quizExamIdStr)) {
            throw new DataInputException("ID Quiz phải là ký tự số");
        }

        Long quizExamId = Long.parseLong(quizExamIdStr);

        QuizExam quizExam = quizExamService.findById(quizExamId).orElseThrow(() -> {
           throw new DataInputException("Kỳ thi không tồn tại");
        });

        Boolean existQuiz = quizService.existsByStudentAndDone(student, false);

        if (existQuiz) {
            throw new DataInputException("Bạn đang có 1 bài kiểm tra chưa làm xong, vui lòng hoàn thành bài kiểm tra đó");
        }

        Quiz quiz = quizService.create(student, quizExam);

//        QuizTestResDTO quizTestResDTO = quizQuestionService.getQuizTestFirst(quiz);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizExamId}/{offsetIndex}")
    public ResponseEntity<?> getQuiz(@PathVariable Long quizExamId, @PathVariable Long offsetIndex) {
        String username = appUtils.getPrincipalUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UnauthorizedException("Vui lòng đăng nhập để sử dụng dịch vụ");
        });

        Student student = studentService.findByUser(user).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        QuizExam quizExam = quizExamService.findById(quizExamId).orElseThrow(() -> {
           throw new DataInputException("Thông tin kỳ thi không tồn tại");
        });

        offsetIndex--;

        if (offsetIndex < 1) {
            offsetIndex = 0L;
        }
        else {
            if (offsetIndex >= quizExam.getNumberQuestion()) {
                offsetIndex = quizExam.getNumberQuestion() - 1;
            }
        }

        Quiz quiz = quizService.findByQuizExamAndStudentAndDone(quizExam, student, false).orElseThrow(() -> {
           throw new DataInputException("Bạn chưa có kỳ thi nào");
        });

        QuizTestResDTO quizTestResDTO = quizQuestionService.getQuizTestQuestion(quiz, offsetIndex);

        return new ResponseEntity<>(quizTestResDTO, HttpStatus.OK);

    }
}
