package com.prajan.FoodReduction.controller;

import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.UserInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private UserInRepository userrepo;

    @GetMapping("/hello")
    public String home(){
        return "hello";
    }

    @GetMapping("/all")
    public List<UserIn> getAllUsers() {
        return userrepo.findAll();
    }
}
