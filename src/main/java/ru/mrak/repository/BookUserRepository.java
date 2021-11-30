package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;

import java.util.List;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, BookUserId>, JpaSpecificationExecutor<BookUser> {

    /**
     * Сбрасывает, для указанного пользователя, книги для чтения
     */
    @Modifying
    @Query(
        value = "update book_user " +
            "set is_reading = false " +
            "where user_id = :userId",
        nativeQuery = true
    )
    void resetIsReadByUserId(@Param("userId") Long userId);

    List<BookUser> findAllByUserAndIsReading(User user, Boolean isReading);
}
