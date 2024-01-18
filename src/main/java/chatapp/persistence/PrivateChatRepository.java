package chatapp.persistence;

import chatapp.domain.entities.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
    @Query("select p from PrivateChat p where p.chatName = :chatName")
    Optional<PrivateChat> findPrivateChatByChatName(@Param("chatName") String chatName);
}