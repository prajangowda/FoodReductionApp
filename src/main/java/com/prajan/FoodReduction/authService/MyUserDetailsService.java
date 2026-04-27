package com.prajan.FoodReduction.authService;

import com.prajan.FoodReduction.model.CustomUserDetails;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserInRepository userrepo;

    public MyUserDetailsService(UserInRepository userrepo) {
        this.userrepo = userrepo;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserIn user=userrepo.findByEmail(email).orElse(null);

        if(user==null)
        {
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomUserDetails(user);
    }
}
