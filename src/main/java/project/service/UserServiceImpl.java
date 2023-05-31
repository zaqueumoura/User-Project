package project.service;

import lombok.RequiredArgsConstructor;
import project.dto.*;
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

    private final ICacheToken cacheToken;

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

    private void descryptoHashPassword(String password, User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())){
            if (user.getAttempts() >= 8){
                user.setStatus(Status.BLOCKED);
                cacheToken.save(user);
                throw new UnauthorizedException("Usuário ou senha incorretos");
            }
            user.setAttempts(user.getAttempts() +1);
            cacheToken.save(user);
            throw new UnauthorizedException("Usuário ou senha incorretos");
        }
        user.setAttempts(0);
        cacheToken.save(user);
    }

    @Override
    public UserAuthenticatedDTO autentication(UserDTO userDTO){
       //var user = cacheToken.getOne(userDTO.getDocumentNumber());
        var user = userRepository.findByDocumentNumber(userDTO.getDocumentNumber()).orElseThrow();
       if (!Status.ACTIVE.equals(user.getStatus())){
           throw new UnauthorizedException("Usuário inativo ou bloqueado");
       }

       descryptoHashPassword(userDTO.getPassword(), user);

       return jwtTokenService.generateToken(user);

    }

    @Override
    public UserCreateResponseDTO getOne(String documentNumber){
        return new UserCreateResponseDTO(userRepository.findByDocumentNumber(documentNumber).orElseThrow(
                ()-> new NotFoundException("conta não entonctrada")));

    }

    public void delete(String documentNumberDelete, String documentNumber){
        var userAdmin = userRepository.findByDocumentNumber(documentNumber).orElseThrow(
                ()-> new NotFoundException("conta não entonctrada"));
        if (!userAdmin.isAdmin()){
            throw new BadRequestException("Usuário não tem permissão para essa operação");
        }
        var userDelete = userRepository.findByDocumentNumber(documentNumberDelete).orElseThrow(
                ()-> new NotFoundException("conta não entonctrada"));

        userRepository.delete(userDelete);
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
