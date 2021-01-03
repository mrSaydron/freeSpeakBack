package ru.mrak.repository;

import ru.mrak.domain.Chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Chat entity.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {

    @Query(value = "select distinct chat from Chat chat left join fetch chat.users",
        countQuery = "select count(distinct chat) from Chat chat")
    Page<Chat> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct chat from Chat chat left join fetch chat.users")
    List<Chat> findAllWithEagerRelationships();

    @Query("select chat from Chat chat left join fetch chat.users where chat.id =:id")
    Optional<Chat> findOneWithEagerRelationships(@Param("id") Long id);
}
