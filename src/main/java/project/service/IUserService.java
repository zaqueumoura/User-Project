package project.service;

import project.dto.UserAuthenticatedDTO;
import project.dto.UserCreateDTO;
import project.dto.UserCreateResponseDTO;
import project.dto.UserDTO;

public interface IUserService {

    UserCreateResponseDTO create(UserCreateDTO userCreateDTO, String documentNumber);

    UserAuthenticatedDTO autentication(UserDTO userDTO);

    UserCreateResponseDTO getOne(String documentNumber);
}
