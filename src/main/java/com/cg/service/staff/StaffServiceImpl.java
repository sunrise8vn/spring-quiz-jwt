package com.cg.service.staff;

import com.cg.model.Role;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.StaffCreReqDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private IStaffRepository staffRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Staff> findAll() {
        return null;
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Optional<Staff> findByUser(User user) {
        return staffRepository.findByUser(user);
    }

    @Override
    public void create(StaffCreReqDTO staffCreReqDTO, Role role) {
        String password = passwordEncoder.encode(staffCreReqDTO.getPassword());
        User user = staffCreReqDTO.toUser(password, role);
        userRepository.save(user);

        Staff staff = staffCreReqDTO.toStaff(user);
        staffRepository.save(staff);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void delete(Staff staff) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
