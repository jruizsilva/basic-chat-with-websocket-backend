package chatapp.controller;

import chatapp.entities.PublicMessage;
import chatapp.http.request.PublicMessageRequest;
import chatapp.service.PublicMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public-messages")
public class PublicMessageController {
    private final PublicMessageService publicMessageService;

    @PostMapping
    public ResponseEntity<PublicMessage> addPublicMessage(@RequestBody @Valid PublicMessageRequest publicMessageRequest) {
        return ResponseEntity.ok(publicMessageService.addPublicMessage(publicMessageRequest));
    }

    @GetMapping
    public ResponseEntity<List<PublicMessage>> findAllPublicMessages() {
        return ResponseEntity.ok(publicMessageService.findAllPublicMessages());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPublicMesaggesBySender(@RequestParam String sender) {
        publicMessageService.deleteAllPublicMesaggesBySender(sender);
        return ResponseEntity.noContent()
                             .build();
    }
}
