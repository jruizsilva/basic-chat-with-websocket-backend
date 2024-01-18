package chatapp.controller;

import chatapp.domain.entities.PrivateChat;
import chatapp.domain.entities.PrivateMessage;
import chatapp.domain.request.PrivateChatRequest;
import chatapp.domain.request.PrivateMessageRequest;
import chatapp.persistence.PrivateChatRepository;
import chatapp.persistence.PrivateMessageRepository;
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
    private final PrivateMessageRepository privateMessageRepository;

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

    @PatchMapping
    public PrivateChat addMessageToChat(@RequestBody @Valid
                                        PrivateMessageRequest privateMessageRequest,
                                        @RequestParam String chatName) {
        Optional<PrivateChat> privateChatOptional = findPrivateChatByChatName(chatName);
        if (privateChatOptional.isEmpty()) {
            throw new RuntimeException("chat not found");
        }
        PrivateChat privateChat = privateChatOptional.get();
        PrivateMessage privateMessage = PrivateMessage.builder()
                                                      .content(privateMessageRequest.getContent())
                                                      .sender(privateMessageRequest.getSender())
                                                      .receiver(privateMessageRequest.getReceiver())
                                                      .build();
        PrivateMessage privateMessageSaved = privateMessageRepository.save(privateMessage);
        privateChat.getMessages()
                   .add(privateMessageSaved);

        return privateChatRepository.save(privateChat);
    }

    @GetMapping
    public Optional<PrivateChat> findPrivateChatByChatName(@RequestParam String chatName) {
        return privateChatRepository.findPrivateChatByChatName(chatName);
    }

}
