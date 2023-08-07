package com.cg.service.staff;

import com.cg.model.Role;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.StaffCreReqDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;


public interface IStaffService extends IGeneralService<Staff, Long> {

    Optional<Staff> findByUser(User user);

    void create(StaffCreReqDTO staffCreReqDTO, Role role);
}
