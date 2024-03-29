package chatapp.entities;

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
@Table(name = "public_messages")
public class PublicMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    private String sender;
    private String content;
    private MessageType type;
    private Date timestamp;

    @PrePersist
    private void antesDePersistir() {
        timestamp = new Date();
    }
}