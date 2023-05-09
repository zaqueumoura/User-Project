package project.service;

import lombok.RequiredArgsConstructor;
import project.dto.UserAuthenticatedDTO;
import project.dto.UserCreateDTO;
import project.dto.UserCreateResponseDTO;
import project.dto.UserDTO;
import project.errors.BadRequestException;
import project.errors.NotFoundException;
import project.errors.UnauthorizedException;
import project.model.User;
import project.repository.UserRepository;
import project.security.IJwtTokenService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;

    private final IJwtTokenService jwtTokenService;

    @Override
    public UserCreateResponseDTO create(UserCreateDTO userCreateDTO, String documentNumber){
        validateCreateUser(documentNumber, userCreateDTO.getDocumentNumber());
        userRepository.save(new User(userCreateDTO, generateHashPassword(userCreateDTO.getPassword())));
        return new UserCreateResponseDTO(userCreateDTO);
    }

    private String generateHashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private void descryptoHashPassword(String password, String passwordSave){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, passwordSave)){
            throw new UnauthorizedException("Usuário ou senha incorretos");
        }

    }

    @Override
    public UserAuthenticatedDTO autentication(UserDTO userDTO){
       var user = userRepository.findByDocumentNumber(userDTO.getDocumentNumber())
               .orElseThrow(() -> new NotFoundException("Usuário ou senha incorretos"));
       descryptoHashPassword(userDTO.getPassword(), user.getPassword());
       return jwtTokenService.generateToken(user);
    }

    @Override
    public UserCreateResponseDTO getOne(String documentNumber){
        return new UserCreateResponseDTO(userRepository.findByDocumentNumber(documentNumber).orElseThrow(
                ()-> new NotFoundException("conta não entonctrada")));

    }

    private void validateCreateUser(String documentAdmin, String documentNew){
        if (documentAdmin.equals(documentNew)){
            throw new BadRequestException("Usuário já existente");
        }
        userRepository.findByDocumentNumber(documentAdmin).ifPresent(user -> {
            if (!user.isAdmin()){
                throw new UnauthorizedException("Usuário não pode cadastrar um novo usuário");
            }
        });
    }

}
