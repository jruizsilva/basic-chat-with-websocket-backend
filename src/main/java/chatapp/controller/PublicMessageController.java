package chatapp.controller;

import chatapp.domain.entities.PublicMessage;
import chatapp.domain.request.PublicMessageRequest;
import chatapp.persistence.PublicMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-messages")
@CrossOrigin
public class PublicMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final PublicMessageRepository publicMessageRepository;

    @PostMapping
    public PublicMessage addPublicMessage(@RequestBody @Valid PublicMessageRequest publicMessageRequest) {
        PublicMessage publicMessage = PublicMessage.builder()
                                                   .content(publicMessageRequest.getContent())
                                                   .sender(publicMessageRequest.getSender())
                                                   .build();
        PublicMessage publicMessageSaved = publicMessageRepository.save(publicMessage);
        List<PublicMessage> publicMessageList = this.findAllPublicMessages();
        messagingTemplate.convertAndSend("/topic/public-messages",
                                         publicMessageList);
        return publicMessageSaved;
    }

    @GetMapping
    public List<PublicMessage> findAllPublicMessages() {
        return publicMessageRepository.findAll();
    }

    @DeleteMapping
    void deleteAllPublicMesaggesBySender(@RequestParam String sender) {
        publicMessageRepository.deleteAllPublicMesaggesBySender(sender);
        List<PublicMessage> publicMessageList = this.findAllPublicMessages();
        messagingTemplate.convertAndSend("/topic/public-messages",
                                         publicMessageList);
    }
}
