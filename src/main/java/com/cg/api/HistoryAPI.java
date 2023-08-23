package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.exception.UnauthorizedException;
import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.model.dto.quizResult.QuizResultResDTO;
import com.cg.service.question.IQuestionService;
import com.cg.service.quiz.IQuizService;
import com.cg.service.quizAnswer.IQuizAnswerService;
import com.cg.service.quizExam.IQuizExamService;
import com.cg.service.quizQuestion.IQuizQuestionService;
import com.cg.service.quizResult.IQuizResultService;
import com.cg.service.student.IStudentService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/histories")
public class HistoryAPI {
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
    private IQuizAnswerService quizAnswerService;

    @Autowired
    private IQuizQuestionService quizQuestionService;

    @Autowired
    private IQuizService quizService;

    @Autowired
    private IQuizResultService quizResultService;


    @GetMapping("/review/{quizId}")
    public ResponseEntity<?> getQuizById(@PathVariable Long quizId) {
        String username = appUtils.getPrincipalUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UnauthorizedException("Vui lòng đăng nhập để sử dụng dịch vụ");
        });

        Student student = studentService.findByUser(user).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        Quiz quiz = quizService.findIdAndByStudentAndDone(quizId, student, true).orElseThrow(() -> {
            throw new DataInputException("Bạn chưa có kỳ thi nào");
        });

        QuizResultResDTO quizResultResDTO = quizResultService.getQuizResultResDTO(quiz);


        return new ResponseEntity<>(quizResultResDTO, HttpStatus.OK);
    }
}
