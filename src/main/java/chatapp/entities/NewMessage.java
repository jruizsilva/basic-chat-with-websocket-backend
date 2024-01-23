package chatapp.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NewMessage {
    private String chatName;
    private String sender;
    private String content;
}
