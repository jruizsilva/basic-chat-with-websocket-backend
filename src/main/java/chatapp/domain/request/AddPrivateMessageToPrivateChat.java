package chatapp.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class AddPrivateMessageToPrivateChat {
    @NotBlank
    private String receiver;
    @NotBlank
    private String sender;
    @NotBlank
    private String content;
    @NotBlank
    private String chatName;
}
