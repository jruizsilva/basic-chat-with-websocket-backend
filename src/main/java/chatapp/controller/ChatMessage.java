package chatapp.controller;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatMessage {
    /*private String content;
    private String sender;*/
    private String id;
    private String sender;
    private String content;
    private Date timestamp;
}
