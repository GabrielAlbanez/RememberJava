package BasicApi.ApiBasic.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import BasicApi.ApiBasic.model.User;
import BasicApi.ApiBasic.repository.UserRepository;

// Removed the alias and will use the fully qualified class name directly in the code

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

            System.out.println("✅ Usuário encontrado: " + user.getEmail());
            System.out.println("🔐 Senha armazenada (criptografada): " + user.getPassword());
            System.out.println("👑 Role: " + user.getRoleUser().name());

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities("ROLE_" + user.getRoleUser().name())
            .build();
    }
}
