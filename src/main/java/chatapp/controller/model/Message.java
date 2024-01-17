package chatapp.controller.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Message {
   /* private String senderName;
    private String receiverName;
    private String message;
    private String data;
    private Status status;*/

    private String nickname;
    private String content;
    private Date timestamp;
}
