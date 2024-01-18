package chatapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToMany(mappedBy = "members")
    @ToString.Exclude
    private List<PrivateChat> privateChats;
}