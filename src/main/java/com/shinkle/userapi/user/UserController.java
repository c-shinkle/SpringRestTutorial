package com.shinkle.userapi.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/new_user")
    public User create(@RequestParam String name) {
        return userService.create(name);
    }

    @GetMapping("/user")
    public User findById(@RequestParam int id) {
        return userService.findById(id);
    }
}
