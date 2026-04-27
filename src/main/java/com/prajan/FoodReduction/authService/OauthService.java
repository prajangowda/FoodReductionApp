package com.prajan.FoodReduction.authService;


import com.prajan.FoodReduction.DTO.LoginResponse;
import com.prajan.FoodReduction.enums.provider;
import com.prajan.FoodReduction.model.CustomUserDetails;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OauthService {
    @Autowired
    private UserInRepository userRepo;

    @Autowired
    private JWTservice jwtservice;


    //OAuth2 method handling
    public LoginResponse handleOauthLoginRequest(OAuth2User oauth2User, String registrationId) {

        String email = oauth2User.getAttribute("email");

        provider providerType = jwtservice.getProviderFromRegistrationId(registrationId);

        String providerId = jwtservice.extractProviderId(oauth2User, registrationId);
        System.out.println(providerType);

        UserIn user = userRepo.findByProviderIdAndProvider(providerId, providerType).orElse(null);

        if (user == null && email != null) {
            user = userRepo.findByEmail(email).orElse(null);
        }

        //new user
        if (user == null) {

            user = new UserIn();
            user.setEmail(email);
            user.setProviderId(providerId);
            user.setProvider(providerType);

            user.setRole(null); // important
            user.setProfileCompleted(false);

            userRepo.save(user);
        }

        //user exits from normal sign up
        else if (user.getProviderId() == null) {

            user.setProviderId(providerId);
            user.setProvider(providerType);

            userRepo.save(user);
        }

        CustomUserDetails principal=new CustomUserDetails(user);
        return new LoginResponse(
                jwtservice.Gettoken(principal),
                user.getRole(),
                user.isProfileCompleted()
        );
    }
}
