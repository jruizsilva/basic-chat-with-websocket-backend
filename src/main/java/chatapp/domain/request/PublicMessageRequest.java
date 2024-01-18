package chatapp.domain.request;

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
}
