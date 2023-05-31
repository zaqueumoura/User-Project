package project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDTO {

    private String token;

    private String documentNumber;

    private String name;

    public UserTokenDTO(UserAuthenticatedDTO newToken, String documentNumber) {
       this.setToken(newToken.getToken());
       this.setName(newToken.getName());
       this.setDocumentNumber(documentNumber);

    }
}
