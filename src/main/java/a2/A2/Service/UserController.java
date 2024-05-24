package a2.A2.Service;

import a2.A2.Utils.JWTUtil;
import a2.A2.Model.User;
import a2.A2.Repository.UserRepository;
import a2.A2.exceptions.MovieDuplicateException;
import a2.A2.exceptions.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@RestController
public class UserController {
    private UserRepository userRepository;
    private JWTUtil jwtUtil;

    public UserController(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    void registerUser(@RequestBody User newUser) throws Exception {
        if(userRepository.findAll().stream().anyMatch(user-> Objects.equals(user.getUsername(), newUser.getUsername())))
        {
            throw new Exception("Username is taken");
        }
        userRepository.save(newUser);
    }

    @PostMapping("/login")
    ResponseEntity<?> logUser(@RequestBody User user){
        for(User u : userRepository.findAll()){
            if(Objects.equals(u.getUsername(), user.getUsername()) && Objects.equals(u.getPassword(), user.getPassword())){
                String token = jwtUtil.generateToken(u.getUserid());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            }
        }
        throw new UserNotFoundException("User not found");
    }

    @PostMapping("/validate")
    ResponseEntity<?> validateToken(@RequestBody String token) throws Exception {
        Jws<Claims> userData = jwtUtil.unpackToken(token);
        Date exp = userData.getBody().getExpiration();
        if(exp.before(new Date()))
            throw new Exception("Invalid token date");
        if(userData.getBody().getSubject() == null)
            throw new Exception("Token body cannot be null");
        long userId = Long.parseLong(userData.getBody().getSubject());
        if(!userRepository.existsById(userId))
            throw new Exception("User does not exist");
        return ResponseEntity.ok().body("Token is valid");
    }
}