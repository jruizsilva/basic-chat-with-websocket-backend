package chatapp.controller;

import chatapp.domain.entities.User;
import chatapp.domain.request.UserRequest;
import chatapp.persistence.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin
public class UserRestController {
    private final UserRepository userRepository;

    @PostMapping
    public User addUser(@RequestBody @Valid
                        UserRequest userRequest) {
        User user = User.builder()
                        .username(userRequest.getUsername())
                        .build();

        return userRepository.save(user);
    }
}
