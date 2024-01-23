package chatapp.controller;

import chatapp.entities.MessageType;
import chatapp.entities.User;
import chatapp.http.request.PublicMessageRequest;
import chatapp.http.request.UserRequest;
import chatapp.persistence.UserRepository;
import chatapp.service.PublicMessageService;
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
    private final PublicMessageService publicMessageService;

    @PostMapping
    public User addUser(@RequestBody @Valid
                        UserRequest userRequest) {
        User user = User.builder()
                        .username(userRequest.getUsername())
                        .build();
        User userCreated = userRepository.save(user);

        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsers());
        PublicMessageRequest publicMessage = PublicMessageRequest.builder()
                                                                 .content(userCreated.getUsername() + " join!")
                                                                 .type(MessageType.JOIN)
                                                                 .sender(userCreated.getUsername())
                                                                 .build();

        publicMessageService.addPublicMessage(publicMessage);

        return userCreated;
    }

    @PatchMapping("/deleteUser")
    private void deleteUser(@RequestBody User userRequest) {
        userRepository.deleteById(userRequest.getId());
        messagingTemplate.convertAndSend("/topic/users",
                                         this.findAllUsers());
        PublicMessageRequest publicMessage = PublicMessageRequest.builder()
                                                                 .content(userRequest.getUsername() + " left!")
                                                                 .type(MessageType.LEAVE)
                                                                 .sender(userRequest.getUsername())
                                                                 .build();
        publicMessageService.addPublicMessage(publicMessage);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
