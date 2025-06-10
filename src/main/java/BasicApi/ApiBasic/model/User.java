package BasicApi.ApiBasic.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "data_create_user", nullable = false)
    private LocalDateTime dataCreateUser;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDataCreateUser() {
        return dataCreateUser;
    }

    public String getEmail() {
        return email;
    }

    public void setDataCreateUser(LocalDateTime dataCreateUser) {
        this.dataCreateUser = dataCreateUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
