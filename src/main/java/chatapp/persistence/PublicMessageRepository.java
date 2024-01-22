package chatapp.persistence;

import chatapp.entities.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {
    @Transactional
    @Modifying
    @Query("delete from PublicMessage p where p.sender = ?1")
    void deleteAllPublicMesaggesBySender(String sender);
}