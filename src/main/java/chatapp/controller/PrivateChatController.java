package chatapp.controller;

import chatapp.domain.entities.PrivateChat;
import chatapp.domain.entities.PrivateMessage;
import chatapp.domain.request.AddPrivateMessageToPrivateChat;
import chatapp.domain.request.PrivateChatRequest;
import chatapp.persistence.PrivateChatRepository;
import chatapp.persistence.PrivateMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private-chat")
@CrossOrigin
public class PrivateChatController {
    private final PrivateChatRepository privateChatRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

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
    public PrivateChat addPrivateMessageToPrivateChat(@RequestBody @Valid
                                                      AddPrivateMessageToPrivateChat privateMessageRequest) {
        Optional<PrivateChat> privateChatOptional = findPrivateChatByChatName(privateMessageRequest.getChatName());
        if (privateChatOptional.isEmpty()) {
            throw new RuntimeException("chat not found");
        }
        PrivateChat privateChat = privateChatOptional.get();
        PrivateMessage privateMessage = PrivateMessage.builder()
                                                      .privateChat(privateChat)
                                                      .content(privateMessageRequest.getContent())
                                                      .sender(privateMessageRequest.getSender())
                                                      .receiver(privateMessageRequest.getReceiver())
                                                      .build();
        PrivateMessage privateMessageSaved = privateMessageRepository.save(privateMessage);
        List<PrivateMessage> messages = privateChat.getMessages();
        messages.add(privateMessageSaved);
        privateChat.setMessages(messages);

        PrivateChat privateChatSaved = privateChatRepository.save(privateChat);

        String chatName = privateChat.getChatName();
        String[] usernames = chatName.split("_");

        for (String username : usernames) {
            messagingTemplate.convertAndSendToUser(username,
                                                   "/queue/messages",
                                                   privateChatSaved);
        }
        return privateChatSaved;
    }

    @GetMapping
    public Optional<PrivateChat> findPrivateChatByChatName(@RequestParam String chatName) {
        return privateChatRepository.findPrivateChatByChatName(chatName);
    }

}
