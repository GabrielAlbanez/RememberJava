package BasicApi.ApiBasic.services;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import BasicApi.ApiBasic.model.User;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        System.out.println("✅ Autenticado com sucesso: " + authentication.getName());

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();
        long expiry = 3600L; // 1 hora em segundos

        String scope = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("basic-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();



        System.out.println("🔐 Gerando token JWT para o usuário: " + jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
