package BasicApi.ApiBasic.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import BasicApi.ApiBasic.Controllers.dto.LoginRequest;
import BasicApi.ApiBasic.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            System.out.println("Tentando autenticar usuário: " + request.email() + request.password());
            String token = authService.authenticate(request.email(), request.password());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (org.springframework.security.core.AuthenticationException e) {
            System.out.println("Erro de autenticação: " + e.getMessage());
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno");
        }
    }

    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
