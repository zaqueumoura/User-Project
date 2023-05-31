package project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.dto.Status;
import project.dto.UserCreateDTO;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "db_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)", updatable = false)
    private UUID identifier = UUID.randomUUID();

    @Column(unique = true)
    private String documentNumber;

    private String password;

    private String name;

    private boolean admin;

    private int attempts;

    @Enumerated(EnumType.STRING)
    private Status status;

    public User(UserCreateDTO userCreateDTO, String password) {
        this.setName(userCreateDTO.getName());
        this.setDocumentNumber(userCreateDTO.getDocumentNumber());
        this.setPassword(password);
        this.setStatus(Status.ACTIVE);
    }
}
