package chatapp.controller;

import chatapp.domain.entities.Status;
import chatapp.domain.entities.User;
import chatapp.domain.request.UserRequest;
import chatapp.persistence.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public User addUser(@RequestBody @Valid
                        UserRequest userRequest) {
        User user = User.builder()
                        .username(userRequest.getUsername())
                        .status(Status.ONLINE)
                        .build();
        User userCreated = userRepository.save(user);

        // Notificar a /topic/users
        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsersOnline());

        return userCreated;
    }

    @PatchMapping("/logout")
    private void logout(@RequestBody User userRequest) {
        User user = userRepository.findUserByUsername(userRequest.getUsername())
                                  .orElseThrow(() -> new RuntimeException("user not found"));
        user.setStatus(Status.OFFLINE);
        userRepository.save(user);
        // Notificar a /topic/users
        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsersOnline());
    }

    @GetMapping
    public List<User> findAllUsersOnline() {
        return userRepository.findAllUsersByStatus(Status.ONLINE);
    }
}
