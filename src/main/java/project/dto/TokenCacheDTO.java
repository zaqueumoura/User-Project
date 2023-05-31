package project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TokenCacheDTO {

    private String name;

    private String documentNumber;

    private String token;

}
