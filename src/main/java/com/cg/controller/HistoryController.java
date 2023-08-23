package com.cg.controller;

import com.cg.exception.UnauthorizedException;
import com.cg.model.Quiz;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.service.quiz.IQuizService;
import com.cg.service.student.IStudentService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/histories")
public class HistoryController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IQuizService quizService;


    @GetMapping("/review")
    public String showReviewPage(@RequestParam("id") Long quizId, Model model) {
        String username = appUtils.getPrincipalUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UnauthorizedException("Vui lòng đăng nhập để sử dụng dịch vụ");
        });

        Student student = studentService.findByUser(user).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        Optional<Quiz> quizOptional = quizService.findIdAndByStudentAndDone(quizId, student, true);

        if (quizOptional.isEmpty()) {
            model.addAttribute("errorMsg", "Bạn chưa đăng ký bài thi này");
            return "error/404";
        }

        QuizExam quizExam = quizOptional.get().getQuizExam();

        String quizExamTitle = quizExam.getTitle();

        model.addAttribute("quizId", quizId.toString());
        model.addAttribute("quizExamTitle", quizExamTitle);

        return "history/review";
    }
}
