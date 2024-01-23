package chatapp.controller;

import chatapp.entities.ChatRoom;
import chatapp.entities.NewMessage;
import chatapp.entities.PrivateMessage;
import chatapp.http.request.AddPrivateMessageToPrivateChat;
import chatapp.http.request.PrivateChatRequest;
import chatapp.persistence.ChatRoomRepository;
import chatapp.persistence.PrivateMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-rooms")
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody @Valid PrivateChatRequest privateChatRequest) {
        Optional<ChatRoom> privateChatOptional = findChatRoomOptionalByChatName(privateChatRequest.getChatName());
        if (privateChatOptional.isPresent()) {
            return privateChatOptional.get();
        }
        ChatRoom chatRoom = ChatRoom.builder()
                                    .chatName(privateChatRequest.getChatName())
                                    .messages(Collections.emptyList())
                                    .build();

        return chatRoomRepository.save(chatRoom);
    }

    @PatchMapping
    public ChatRoom addPrivateMessageToPrivateChat(@RequestBody @Valid
                                                   AddPrivateMessageToPrivateChat privateMessageRequest) {
        System.out.println("addPrivateMessageToPrivateChat");
        Optional<ChatRoom> privateChatOptional = findChatRoomOptionalByChatName(privateMessageRequest.getChatName());
        if (privateChatOptional.isEmpty()) {
            throw new RuntimeException("chat not found");
        }
        ChatRoom chatRoom = privateChatOptional.get();
        PrivateMessage privateMessage = PrivateMessage.builder()
                                                      .chatRoom(chatRoom)
                                                      .content(privateMessageRequest.getContent())
                                                      .sender(privateMessageRequest.getSender())
                                                      .receiver(privateMessageRequest.getReceiver())
                                                      .build();
        PrivateMessage privateMessageSaved = privateMessageRepository.save(privateMessage);
        List<PrivateMessage> messages = chatRoom.getMessages();
        messages.add(privateMessageSaved);
        chatRoom.setMessages(messages);

        String chatName = chatRoom.getChatName();
        String[] usernames = chatName.split("_");
        ChatRoom chatRoomSaved = chatRoomRepository.save(chatRoom);

        for (String username : usernames) {
            messagingTemplate.convertAndSendToUser(username,
                                                   "/queue/messages",
                                                   chatRoomSaved);
        }

        messagingTemplate.convertAndSendToUser(privateMessageRequest.getReceiver(),
                                               "/notification",
                                               NewMessage.builder()
                                                         .chatName(privateMessageRequest.getChatName())
                                                         .sender(privateMessageRequest.getSender())
                                                         .content(privateMessageRequest.getContent())
                                                         .build());
        return chatRoomSaved;
    }

    @GetMapping
    public Optional<ChatRoom> findChatRoomOptionalByChatName(@RequestParam String chatName) {
        return chatRoomRepository.findChatRoomByChatName(chatName);
    }

    @GetMapping("/{chatName}")
    public ResponseEntity<ChatRoom> findChatRoomByChatName(@PathVariable("chatName") String chatName) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByChatName(chatName)
                                              .orElseThrow(() -> new RuntimeException("chat room not found"));
        return ResponseEntity.ok(chatRoom);
    }

}
