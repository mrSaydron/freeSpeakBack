package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.User;
import ru.mrak.domain.UserDictionary;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDictionaryRepository extends JpaRepository<UserDictionary, Long>, JpaSpecificationExecutor<UserDictionary> {

    Optional<UserDictionary> findFirstByUser(User user);
    List<UserDictionary> findByUser(User user);

}
