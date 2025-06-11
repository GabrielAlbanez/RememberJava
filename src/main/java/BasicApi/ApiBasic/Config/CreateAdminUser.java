package BasicApi.ApiBasic.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import BasicApi.ApiBasic.Enum.RoleUser;
import BasicApi.ApiBasic.model.User;
import BasicApi.ApiBasic.repository.UserRepository;


@Configuration
public class CreateAdminUser {
    
    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder encoder) {
        return args->{
            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
                User admin = new User();
                admin.setName("Administrador");
                admin.setEmail("admin@admin.com");
                admin.setPassword(encoder.encode("Gabriel1539@@")); // 🔐 escolha forte em produção
                admin.setRoleUser(RoleUser.ADMIN);
                admin.setDataCreateUser(java.time.LocalDateTime.now());

                userRepository.save(admin);
                System.out.println("✔ Admin criado com sucesso.");
            } else {
                System.out.println("✔ Admin já existe.");
            }
        };
    }
}
