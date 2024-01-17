package chatapp.persistence;

import chatapp.domain.entities.Status;
import chatapp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.status = ?1")
    List<User> findAllUsersByStatus(Status status);
    @Query("select u from User u where u.username = ?1")
    Optional<User> findUserByUsername(String username);
}