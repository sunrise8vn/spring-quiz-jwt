package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.UnauthorizedException;
import com.cg.model.*;
import com.cg.model.dto.StaffCreReqDTO;
import com.cg.model.dto.UserLoginReqDTO;
import com.cg.model.dto.student.StudentCreReqDTO;
import com.cg.model.enums.EUserRole;
import com.cg.service.jwt.JwtService;
import com.cg.service.role.IRoleService;
import com.cg.service.staff.IStaffService;
import com.cg.service.student.IStudentService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/staffs/register")
    public ResponseEntity<?> staffRegister(@Valid @RequestBody StaffCreReqDTO staffCreReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<User> userOptional = userService.findByUsername(staffCreReqDTO.getUsername());

        if (userOptional.isPresent()) {
            throw new EmailExistsException("Tài khoản đã tồn tại");
        }

//        EUserRole eUserRole = EUserRole.fromString(staffCreReqDTO.getRoleCode().toUpperCase());
//
//        if (!eUserRole.equals(EUserRole.ROLE_ADMIN) && !eUserRole.equals(EUserRole.ROLE_STAFF)) {
//            throw new DataInputException("Thông tin vai trò không hợp lệ, vui lòng kiểm tra lại dữ liệu");
//        }

        Role role = roleService.findByCode(staffCreReqDTO.getRoleCode().toUpperCase()).orElseThrow(() -> {
            throw new DataInputException("Thông tin vai trò không hợp lệ, vui lòng kiểm tra lại dữ liệu");
        });

        try {
            staffService.create(staffCreReqDTO, role);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại thông tin");
        }
    }

    @PostMapping("/staffs/login")
    public ResponseEntity<?> staffLogin(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginReqDTO.getUsername(), userLoginReqDTO.getPassword())
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Thông tin tài khoản hoặc mật khẩu không hợp lệ");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByUsername(userLoginReqDTO.getUsername());

        Staff staff = staffService.findByUser(currentUser.get()).orElseThrow(() -> {
            throw new DataInputException("Tài khoản nhân viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.get().getId(),
                userDetails.getUsername(),
                staff.getFullName(),
                currentUser.get().getRole().getCode()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(1000 * 60 * 60)
                .domain("localhost")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }

    @PostMapping("/students/register")
    public ResponseEntity<?> studentRegister(@Valid @RequestBody StudentCreReqDTO studentCreReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<User> userOptional = userService.findByUsername(studentCreReqDTO.getUsername());

        if (userOptional.isPresent()) {
            throw new EmailExistsException("Tài khoản đã tồn tại");
        }

        Role role = roleService.findByCode(EUserRole.ROLE_STUDENT.getValue()).get();

        try {
            studentService.create(studentCreReqDTO, role);

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại thông tin");
        }
    }

    @PostMapping("/students/login")
    public ResponseEntity<?> studentLogin(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginReqDTO.getUsername(), userLoginReqDTO.getPassword())
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Thông tin tài khoản hoặc mật khẩu không hợp lệ");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByUsername(userLoginReqDTO.getUsername());

        Student student = studentService.findByUser(currentUser.get()).orElseThrow(() -> {
            throw new DataInputException("Tài khoản học viên không tồn tại, vui lòng kiểm tra lại thông tin");
        });

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.get().getId(),
                userDetails.getUsername(),
                student.getFullName(),
                currentUser.get().getRole().getCode()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(1000 * 60 * 60)
                .domain("localhost")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }
}
