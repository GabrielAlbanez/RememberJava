package BasicApi.ApiBasic.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BasicApi.ApiBasic.model.User;
import BasicApi.ApiBasic.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } 

    // Define your endpoints here, for example:

    @GetMapping
    public List<User>listAllUsers(){
        return userService.listAllUsers();
    }

    @PostMapping("/create") 
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    


}
