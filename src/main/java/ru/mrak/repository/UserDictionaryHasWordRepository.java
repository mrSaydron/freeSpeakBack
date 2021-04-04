package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.User;
import ru.mrak.domain.UserDictionary;
import ru.mrak.domain.UserDictionaryHasWord;
import ru.mrak.domain.Word;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface UserDictionaryHasWordRepository extends JpaRepository<UserDictionaryHasWord, Long>, JpaSpecificationExecutor<UserDictionaryHasWord> {

    @Query("select h from UserDictionaryHasWord h where h.dictionary.user = :user and h.word in :words")
    List<UserDictionaryHasWord> findByWordsAndUser(List<Word> words, User user);

    @Query("select h from UserDictionaryHasWord h where h.dictionary.user = :user and h.word = :word")
    Optional<UserDictionaryHasWord> findByWordAndUser(Word word, User user);

//    @Modifying
//    @Query(value = "delete from user_dictionary_has_word h " +
//        "where h.word_id = :wordId " +
//        "and h.user_dictionary_id in (select ud.id from user_dictionary ud where ud.user_id = :userId)",
//    nativeQuery = true)
//    void deleteByWordAndUser(@Param("wordId") Long wordId,
//                             @Param("userId") Long userId);

    @Modifying
    @Query(value = "delete from UserDictionaryHasWord h where h.word = :word and h.dictionary in :userDictionaries")
    void deleteByWordAndUser(Word word, List<UserDictionary> userDictionaries);

}
