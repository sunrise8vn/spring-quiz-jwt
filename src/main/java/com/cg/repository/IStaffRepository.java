package com.cg.repository;

import com.cg.model.Staff;
import com.cg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IStaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUser(User user);
}
