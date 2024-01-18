package chatapp.controller;

import chatapp.domain.entities.PrivateChat;
import chatapp.domain.request.PrivateChatRequest;
import chatapp.persistence.PrivateChatRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private-chat")
@CrossOrigin
public class PrivateChatController {
    private final PrivateChatRepository privateChatRepository;

    @PostMapping
    public PrivateChat createPrivateChat(@RequestBody @Valid PrivateChatRequest privateChatRequest) {
        Optional<PrivateChat> privateChatOptional = findPrivateChatByChatName(privateChatRequest.getChatName());
        if (privateChatOptional.isPresent()) {
            return privateChatOptional.get();
        }
        PrivateChat privateChat = PrivateChat.builder()
                                             .chatName(privateChatRequest.getChatName())
                                             .messages(Collections.emptyList())
                                             .build();

        return privateChatRepository.save(privateChat);
    }

    @GetMapping
    public Optional<PrivateChat> findPrivateChatByChatName(@RequestParam String chatName) {
        return privateChatRepository.findPrivateChatByChatName(chatName);
    }

}
