package com.userservice.userservice.controllers;

import com.userservice.userservice.dtos.*;
import com.userservice.userservice.exceptions.InvalidPasswordException;
import com.userservice.userservice.exceptions.InvalidTokenException;
import com.userservice.userservice.models.Token;
import com.userservice.userservice.models.User;
import com.userservice.userservice.repositories.UserRepository;
import com.userservice.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){
        User user = userService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getName());
        return UserDto.from(user);
    }
    @PostMapping("/login")
    public LogInResponseDto login(@RequestBody LoginRequestDto requestDto) throws InvalidPasswordException {
        Token token = userService.login(requestDto.getEmail(),
                                        requestDto.getPassword());

        LogInResponseDto responseDto = new LogInResponseDto();
        responseDto.setToken(token);

        return responseDto;
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws InvalidTokenException{
        ResponseEntity<Void> response = null;
        try{
            userService.logout(logoutRequestDto.getToken());
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Invalid token passed.");
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token")String token) throws InvalidTokenException{
        return UserDto.from(userService.validateToken(token));
    }
    @GetMapping("/{id}")
    public String getResponse(@PathVariable("id")Long id){
        System.out.println("Received the request for id : " + id);
        return new String("Got the request for user id : " + id);
    }
}
