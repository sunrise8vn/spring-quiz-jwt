package com.cg.service.student;

import com.cg.model.Role;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.model.dto.student.StudentCreReqDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IStudentService extends IGeneralService<Student, Long> {

    Optional<Student> findByUser(User user);

    void create(StudentCreReqDTO studentCreReqDTO, Role role);
}
