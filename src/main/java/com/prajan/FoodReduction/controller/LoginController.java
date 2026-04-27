package com.prajan.FoodReduction.controller;

import com.prajan.FoodReduction.DTO.LoginRequest;
import com.prajan.FoodReduction.DTO.LoginResponse;
import com.prajan.FoodReduction.DTO.SingupRequest;
import com.prajan.FoodReduction.authService.AuthService;
import com.prajan.FoodReduction.enums.Role;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private UserInRepository userrepo;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        LoginResponse response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    //signUp
    @PostMapping("/signup/")
    public String signUpDonor(@RequestBody SingupRequest signupdto) {
        return  authService.signup(signupdto);
    }

//    //singUp for volunteer
//    @PostMapping("/signup/volunteer")
//    public String signUpVolunteer(@RequestBody SingupRequest signupdto) {
//        return  authService.volunteerSignup(signupdto);
//    }
//
//    //singUp for NGO
//    @PostMapping("/signup/ngo")
//    public String signUpngo(@RequestBody SingupRequest signupdto) {
//        return  authService.ngoSignup(signupdto);
//    }
}
