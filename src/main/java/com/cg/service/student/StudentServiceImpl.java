package com.cg.service.student;


import com.cg.model.Role;
import com.cg.model.Staff;
import com.cg.model.Student;
import com.cg.model.User;
import com.cg.model.dto.student.StudentCreReqDTO;
import com.cg.repository.IStudentRepository;
import com.cg.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByUser(User user) {
        return studentRepository.findByUser(user);
    }

    @Override
    public void create(StudentCreReqDTO studentCreReqDTO, Role role) {
        String password = passwordEncoder.encode(studentCreReqDTO.getPassword());
        User user = studentCreReqDTO.toUser(password, role);
        userRepository.save(user);

        Student student = studentCreReqDTO.toStudent(user);
        studentRepository.save(student);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
