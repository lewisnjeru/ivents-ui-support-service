package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    @Column(name = "failed_login_count")
    private Integer failedLoginCount = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "created_by")
    private String createdBy = "system";

    @Column(name = "modified_by")
    private String modifiedBy = "system";

    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn = LocalDateTime.now();
}