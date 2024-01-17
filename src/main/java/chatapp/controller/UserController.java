package chatapp.controller;

import chatapp.domain.entities.User;
import chatapp.domain.request.UserRequest;
import chatapp.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @MessageMapping("/users/addUser") // /app/users/addUser
    @SendTo("/users/public")
    private List<User> addUser(@Payload UserRequest userRequest) {
        User user = User.builder()
                        .username(userRequest.getUsername())
                        .build();
        userRepository.save(user);
        return userRepository.findAll();
    }
}
