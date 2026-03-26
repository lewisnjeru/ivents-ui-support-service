package ivents.ivents_ui_support.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "module")
    private String module;

    @Column(name = "sub_module")
    private String subModule;

    @Column(name = "uri")
    private String uri;

    @Column(name = "description")
    private String description;

    @Column(name = "verb")
    private String verb;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
}