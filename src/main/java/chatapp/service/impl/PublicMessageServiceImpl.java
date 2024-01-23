package chatapp.service.impl;

import chatapp.entities.PublicMessage;
import chatapp.http.request.PublicMessageRequest;
import chatapp.persistence.PublicMessageRepository;
import chatapp.service.PublicMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicMessageServiceImpl implements PublicMessageService {
    private final PublicMessageRepository publicMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public PublicMessage addPublicMessage(PublicMessageRequest publicMessageRequest) {
        PublicMessage publicMessage = PublicMessage.builder()
                                                   .content(publicMessageRequest.getContent())
                                                   .sender(publicMessageRequest.getSender())
                                                   .type(publicMessageRequest.getType())
                                                   .build();
        PublicMessage publicMessageSaved = publicMessageRepository.save(publicMessage);
        List<PublicMessage> publicMessageList = this.findAllPublicMessages();
        messagingTemplate.convertAndSend("/topic/public-messages",
                                         publicMessageList);

        return publicMessageSaved;
    }

    @Override
    public List<PublicMessage> findAllPublicMessages() {
        return publicMessageRepository.findAll();
    }

    @Override
    public void deleteAllPublicMesaggesBySender(String sender) {
        publicMessageRepository.deleteAllPublicMesaggesBySender(sender);
        List<PublicMessage> publicMessageList = this.findAllPublicMessages();
        messagingTemplate.convertAndSend("/topic/public-messages",
                                         publicMessageList);
    }
}
