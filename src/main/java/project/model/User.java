package project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.dto.UserCreateDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    private String documentNumber;

    private String password;

    private String name;

    private boolean admin;

    public User(UserCreateDTO userCreateDTO, String password) {
        this.setName(userCreateDTO.getName());
        this.setDocumentNumber(userCreateDTO.getDocumentNumber());
        this.setPassword(password);
    }
}
