package chatapp.http.request;

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
