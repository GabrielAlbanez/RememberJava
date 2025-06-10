package BasicApi.ApiBasic.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BasicApi.ApiBasic.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Additional custom query methods can be defined here if needed


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
        
}
