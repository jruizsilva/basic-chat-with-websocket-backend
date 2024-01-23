package chatapp.http.request;

import chatapp.entities.MessageType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PublicMessageRequest {
    private String sender;
    private String content;
    private MessageType type;
}
