package project.dto;

import lombok.Getter;
import lombok.Setter;
import project.model.User;

@Getter
@Setter
public class UserCreateResponseDTO {
    private String name;
    private String documentNumber;

    public UserCreateResponseDTO(UserCreateDTO userCreateDTO) {
        this.setName(userCreateDTO.getName());
        this.setDocumentNumber(userCreateDTO.getDocumentNumber());
    }

    public UserCreateResponseDTO(User user) {
        this.setName(user.getName());
        this.setDocumentNumber(user.getDocumentNumber());

    }
}
