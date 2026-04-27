package com.prajan.FoodReduction.authService;

import com.prajan.FoodReduction.DTO.LoginRequest;
import com.prajan.FoodReduction.DTO.LoginResponse;
import com.prajan.FoodReduction.DTO.SingupRequest;
import com.prajan.FoodReduction.enums.Role;
import com.prajan.FoodReduction.enums.provider;
import com.prajan.FoodReduction.model.*;
import com.prajan.FoodReduction.repository.DonorRepository;
import com.prajan.FoodReduction.repository.UserInRepository;
import com.prajan.FoodReduction.repository.VolunteerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserInRepository userRepo;

    @Autowired
    private DonorRepository  donorRepo;

    @Autowired
    private VolunteerRepository volunteerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private JWTservice jwtservice;

    public LoginResponse login(LoginRequest LoginDto)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(LoginDto.getEmail(),LoginDto.getPassword()));

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        String token=jwtservice.Gettoken(userPrincipal);
        Role role=userPrincipal.getRole();
        return new LoginResponse(token,role);

    }

    @Transactional
    public String signup(SingupRequest signupdto) {

        UserIn user = userRepo.findByEmail(signupdto.getEmail()).orElse(null);

        if (user != null) {
            throw new RuntimeException("User already exists");
        }

        user = UserIn.builder()
                .email(signupdto.getEmail())
                .password(passwordEncoder.encode(signupdto.getPassword()))
                .provider(provider.EMAIL)
                .role(null) // important
                .build();

        userRepo.save(user);

        return "User created";
    }


}
