package project.security;

import project.dto.UserAuthenticatedDTO;
import project.model.User;

public interface IJwtTokenService {
    UserAuthenticatedDTO generateToken(User authentication);

    String getDocumentNumberFromToken(String token);

    void validateToken(String token);
}
