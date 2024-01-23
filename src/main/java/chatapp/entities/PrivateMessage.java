package chatapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@Entity
@Table(name = "private_messages")
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    private String receiver;
    private String sender;
    private String content;
    private Date timestamp;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;

    @PrePersist
    private void antesDePersistir() {
        timestamp = new Date();
    }
}
