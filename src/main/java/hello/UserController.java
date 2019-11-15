package hello;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private Map<Integer, User> db = new HashMap<Integer, User>(){{
        put(1, new User(1, "Christian"));
    }};

    @RequestMapping("/users")
    public User users(@RequestParam(value="id") int id) {
        return db.get(id);
    }

    @GetMapping("/user/{id}/{name}")
    @ResponseBody
    public String user(@PathVariable int id, @PathVariable String name) {
        db.put(id, new User(id, name));
        return String.format("Stored: id=%d, name=%s", id, name);
    }
}
