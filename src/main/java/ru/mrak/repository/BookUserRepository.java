package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.BookUser;
import ru.mrak.domain.BookUserId;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, BookUserId>, JpaSpecificationExecutor<BookUser> {

}
