package com.valentinstamate.prefoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/users"})
public class UserController {

    @GetMapping
    public String get() {
        return "Ana are mere";
    }

}
