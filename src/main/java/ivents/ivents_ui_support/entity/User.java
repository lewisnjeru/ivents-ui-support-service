package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "failed_login")
    private Integer failedLogin = 0;

    @Column(name = "locked_until")
    private Instant lockedUntil;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_by")
    private String createdBy = "system";

    @Column(name = "modified_by")
    private String modifiedBy = "system";

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "modified_on")
    private Instant modifiedOn = Instant.now();
}