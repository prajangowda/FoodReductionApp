package com.prajan.FoodReduction.controller;


import com.prajan.FoodReduction.DTO.CompleteProfileRequest;
import com.prajan.FoodReduction.DTO.LoginResponse;
import com.prajan.FoodReduction.authService.JWTservice;
import com.prajan.FoodReduction.model.CustomUserDetails;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import com.prajan.FoodReduction.userservice.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final RoleService roleService;
    private final UserInRepository userRepository;
    private final JWTservice jwtservice;

    @PostMapping("/complete-profile")
    public ResponseEntity<?> completeProfile(
            @RequestBody CompleteProfileRequest req,
            Authentication authentication
    ) {

        String email = authentication.getName(); // from JWT

        UserIn user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        roleService.completeProfile(user, req);

        CustomUserDetails userPrincipal =new CustomUserDetails(user);
        String token=jwtservice.Gettoken(userPrincipal);
        return ResponseEntity.ok(new LoginResponse(token,user.getRole(),user.isProfileCompleted()));
    }
}
