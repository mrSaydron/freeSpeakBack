package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Query("select h from UserDictionaryHasWord h where h.dictionary.user = :user and h.word.id in :wordIds")
    List<UserDictionaryHasWord> findByWordIdsAndUser(List<Long> wordIds, User user);

    @Modifying
    @Query(value = "delete from UserDictionaryHasWord h where h.word = :word and h.dictionary in :userDictionaries")
    void deleteByWordAndUserDictionaries(Word word, List<UserDictionary> userDictionaries);

    @Modifying
    @Query(value = "delete from UserDictionaryHasWord h where h.word.id in :wordIds and h.dictionary in :userDictionaries")
    void deleteByWordIdsAndUserDictionaries(List<Long> wordIds, List<UserDictionary> userDictionaries);
}
