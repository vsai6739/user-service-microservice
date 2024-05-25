package com.userservice.userservice.services;

import com.userservice.userservice.exceptions.InvalidPasswordException;
import com.userservice.userservice.exceptions.InvalidTokenException;
import com.userservice.userservice.models.Token;
import com.userservice.userservice.models.User;
import com.userservice.userservice.repositories.TokenRepository;
import com.userservice.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository ,
                       TokenRepository tokenRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User signup(String email , String password , String name){
        // Check whether user present with given email id
        // If not create User ow return the user.
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }
    public Token login(String email , String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            //User with given email isn't present in DB.
            return null;
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            //throw an exception
            throw new InvalidPasswordException("Please enter correct password");
        }

        //Login successful, generate a new token.
        Token token = generateToken(user);

        return tokenRepository.save(token);
    }
    private Token generateToken(User user) {
        LocalDate currentTime = LocalDate.now(); // current time.
        LocalDate thirtyDaysFromCurrentTime = currentTime.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysFromCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryAt(expiryDate);

        //Token value is a randomly generated String of 128 characters.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        return token;
    }

    public void logout(String tokenValue){
        Optional<Token> optionalUser = tokenRepository.findByValueAndDeleted(tokenValue,false);
        if(optionalUser.isEmpty()){
            throw new InvalidTokenException("Token is invalid");
        }
        Token token = optionalUser.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }
    public User validateToken(String token){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token,false);
        if(optionalToken.isEmpty()) throw new InvalidTokenException("Please enter valid Token");
        return optionalToken.get().getUser();
    }
}
