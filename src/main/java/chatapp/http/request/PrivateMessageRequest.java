package chatapp.http.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PrivateMessageRequest {
    @NotBlank
    private String receiver;
    @NotBlank
    private String sender;
    @NotBlank
    private String content;
}

