package chatapp.controller;

import chatapp.domain.entities.PrivateMessage;
import chatapp.domain.request.PrivateMessageRequest;
import chatapp.persistence.PrivateMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private-messages")
@CrossOrigin
public class PrivateMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final PrivateMessageRepository privateMessageRepository;

    @PostMapping
    public void addPrivateMessage(@RequestBody @Valid
                                  PrivateMessageRequest privateMessageRequest) {
        PrivateMessage privateMessage = PrivateMessage.builder()
                                                      .content(privateMessageRequest.getContent())
                                                      .sender(privateMessageRequest.getSender())
                                                      .receiver(privateMessageRequest.getReceiver())
                                                      .build();
        privateMessageRepository.save(privateMessage);

        messagingTemplate.convertAndSendToUser(privateMessage.getReceiver(),
                                               "/queue/messages",
                                               privateMessage);
    }


}
