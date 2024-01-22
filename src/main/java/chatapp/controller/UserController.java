package chatapp.controller;

import chatapp.entities.User;
import chatapp.http.request.UserRequest;
import chatapp.persistence.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public User addUser(@RequestBody @Valid
                        UserRequest userRequest) {
        User user = User.builder()
                        .username(userRequest.getUsername())
                        .build();
        User userCreated = userRepository.save(user);

        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsers());

        return userCreated;
    }

    @PatchMapping("/deleteUser")
    private void deleteUser(@RequestBody User userRequest) {
        userRepository.deleteById(userRequest.getId());
        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsers());
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
