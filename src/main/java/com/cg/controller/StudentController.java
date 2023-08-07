package com.cg.controller;

import com.cg.exception.DataInputException;
import com.cg.exception.UnauthorizedException;
import com.cg.model.QuizExam;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.service.quiz.IQuizService;
import com.cg.service.quizExam.IQuizExamService;
import com.cg.service.student.IStudentService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IQuizService quizService;

    @Autowired
    private IQuizExamService quizExamService;

    @GetMapping
    public String showHomePage() {
        return "student/home";
    }

    @GetMapping("/quiz")
    public String showQuizPage(@RequestParam Long id, @RequestParam Long page, Model model) {
        String username = appUtils.getPrincipalUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> {
            throw new UnauthorizedException("Vui lòng đăng nhập để sử dụng dịch vụ");
        });

        Student student = studentService.findByUser(user).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        Optional<QuizExam> quizExam = quizExamService.findById(id);

        if (quizExam.isEmpty()) {
            model.addAttribute("errorMsg", "Kỳ thi không tồn tại");
            return "error/404";
        }

        Boolean existQuiz = quizService.existsByStudentAndDone(student, false);

        if (!existQuiz) {
            model.addAttribute("errorMsg", "Bạn chưa đăng ký bài thi này");
            return "error/404";
        }

        model.addAttribute("quizExamId", id);
        model.addAttribute("page", page);

        return "student/quiz";
    }
}
