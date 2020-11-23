package com.shinkle.userapi.user;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final Map<Integer, User> database;

    public UserService(Map<Integer, User> database) {
        this.database = database;
        database.put(1, new User(1, "Christian"));
    }

    public User create(String name) {
        int id = database.size() + 1;
        User user = new User(id, name);
        database.put(id, user);
        return user;
    }

    public User findById(int id) {
        Optional<User> maybeUser = Optional.ofNullable(database.get(id));
        return maybeUser.orElseThrow(() -> new UserNotFound("Could not find User with id: " + id));
    }
}
