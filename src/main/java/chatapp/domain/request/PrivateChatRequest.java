package chatapp.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PrivateChatRequest {
    private String chatName;
}
