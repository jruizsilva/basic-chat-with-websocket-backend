package chatapp.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PrivateMessageRequest {
    private String receiver;
    private String sender;
    private String content;
}

