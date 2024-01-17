package chatapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    @Column(nullable = false,
            unique = true,
            updatable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private Status status;
}