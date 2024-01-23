package chatapp.service;

import chatapp.entities.PublicMessage;
import chatapp.http.request.PublicMessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublicMessageService {
    PublicMessage addPublicMessage(PublicMessageRequest publicMessageRequest);
    List<PublicMessage> findAllPublicMessages();
    void deleteAllPublicMesaggesBySender(String sender);
}
