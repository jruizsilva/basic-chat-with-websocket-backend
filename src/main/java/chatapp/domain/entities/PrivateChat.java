package chatapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "private_chat")
public class PrivateChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Column(unique = true)
    private String chatName;

    @OneToMany(mappedBy = "privateChat")
    @ToString.Exclude
    private List<PrivateMessage> messages;
}