package BasicApi.ApiBasic.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import BasicApi.ApiBasic.Controllers.dto.CreateUser;
import BasicApi.ApiBasic.Enum.RoleUser;
import BasicApi.ApiBasic.model.User;
import BasicApi.ApiBasic.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAllUsers() {
        return userRepository.findAll();    
    }

    public String createUser(CreateUser userDto) {

        if (userExistsByEmail(userDto.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if(userDto.name() == null || userDto.email() == null || userDto.password() == null) {
            throw new IllegalArgumentException("Nome, email e senha são obrigatórios");
        }

        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRoleUser(RoleUser.USER);
        user.setDataCreateUser(LocalDateTime.now());
        userRepository.save(user);
        return "usuario criado com sucesso!";
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(UUID id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setDataCreateUser(java.time.LocalDateTime.now());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public void deleteUseByEmail(String email) {
        if (!userExistsByEmail(email)) {
            throw new IllegalArgumentException("Email não encontrado");
        }
        userRepository.deleteByEmail(email);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
