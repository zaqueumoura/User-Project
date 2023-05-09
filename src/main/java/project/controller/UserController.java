package project.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import project.dto.UserAuthenticatedDTO;
import project.dto.UserCreateDTO;
import project.dto.UserCreateResponseDTO;
import project.dto.UserDTO;
import project.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;


    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public UserAuthenticatedDTO authentication(@RequestBody UserDTO userDTO){
        return userService.autentication(userDTO);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreateResponseDTO create(@RequestBody UserCreateDTO userDTO,
                                        @RequestHeader String documentNumber){
        return userService.create(userDTO, documentNumber);
    }


    @GetMapping("/get-one")
    @ResponseStatus(HttpStatus.OK)
    public UserCreateResponseDTO getOne(@RequestHeader String documentNumber){
        return userService.getOne(documentNumber);
    }

}
