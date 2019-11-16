package hello;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private Map<Integer, User> db;

    public UserController() {
        db = new HashMap<>();
        db.put(1, new User(1, "Christian"));
    }

    @RequestMapping("/user")
    public User user(@RequestParam(value="id") int id) {
        return db.get(id);
    }

    @PostMapping("/new_user")
    public User newUser(@RequestParam("id") int id, @RequestParam String name) {
        User u = new User(id, name);
        db.put(id, u);
        return u;
    }
}
