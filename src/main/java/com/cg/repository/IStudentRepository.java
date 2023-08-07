package com.cg.repository;

import com.cg.model.Student;
import com.cg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);
}
