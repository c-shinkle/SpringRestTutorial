package com.shinkle.hello.user;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final Map<Integer, User> db;

    public UserController() {
        db = new HashMap<>();
        db.put(1, new User(1, "Christian"));
    }

    @GetMapping("/user")
    public User findById(@RequestParam(value = "id") int id) {
        Optional<User> maybeUser = Optional.ofNullable(db.get(id));
        return maybeUser.orElseThrow(() -> new IllegalArgumentException("Could not find User with id: " + id));
    }

    @PostMapping("/new_user")
    public User create(@RequestParam String name) {
        int id = db.size() + 1;
        User u = new User(id, name);
        db.put(id, u);
        return u;
    }
}
