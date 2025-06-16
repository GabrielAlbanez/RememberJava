package BasicApi.ApiBasic.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import BasicApi.ApiBasic.Controllers.dto.CreateUser;
import BasicApi.ApiBasic.Controllers.dto.ResponseDeleteUser;
import BasicApi.ApiBasic.Controllers.dto.ResponseUserCreate;
import BasicApi.ApiBasic.Enum.RoleUser;
import BasicApi.ApiBasic.model.User;
import BasicApi.ApiBasic.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Define your endpoints here, for example:

    @GetMapping
    public List<User> listAllUsers() {
        return userService.listAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseUserCreate> createUser(@RequestBody CreateUser userDto) {
        try {
            String message = userService.createUser(userDto);
            return ResponseEntity.ok(new ResponseUserCreate(message)); // Retorna "usuario criado com sucesso!"
        } catch (IllegalArgumentException e) {
            // Retorna erro 400 com mensagem
            return ResponseEntity.badRequest().body(new ResponseUserCreate(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseUserCreate("Erro interno no servidor"));
        }
    }

    @DeleteMapping("/deleteUser/{email}") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDeleteUser> deleteUser(@PathVariable  String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDeleteUser("User not found")); // Retorna "Usuario não encontrado!"
        }

        userService.deleteUseByEmail(email);
        return ResponseEntity.ok(new ResponseDeleteUser("User deleted successfully")); // Retorna "Usuario deletado com sucesso!"
    } 
    

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promote/{id}")
    public ResponseEntity<?> promoteUser(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (user.getRoleUser() == RoleUser.ADMIN) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already an admin");
        }

        user.setRoleUser(RoleUser.ADMIN);
        userService.updateUser(id, user);

        return ResponseEntity.ok("User promoted to admin successfully");
    }

}
