package BasicApi.ApiBasic.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import BasicApi.ApiBasic.Controllers.dto.LoginRequest;
import BasicApi.ApiBasic.Controllers.dto.ResponseLoginUser;
import BasicApi.ApiBasic.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginUser> login(@RequestBody LoginRequest request) {
        System.out.println("Iniciando processo de login para: " + request.email() + " com senha: " + request.password());
        try {
            if(request.email() == null || request.password() == null) {
                return ResponseEntity.status(401).body(new ResponseLoginUser("Email e senha são obrigatórios"));
            }
            System.out.println("Tentando autenticar usuário: " + request.email() + request.password());
            String token = authService.authenticate(request.email(), request.password());
            return ResponseEntity.ok(new ResponseLoginUser(token));
        } catch (org.springframework.security.core.AuthenticationException e) {
            System.out.println("Erro de autenticação: " + e.getMessage());
            return ResponseEntity.status(401).body(new ResponseLoginUser("Email ou senha inválidos"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseLoginUser("Erro interno"));
        }
    }

}
