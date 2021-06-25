package com.library.repository;

import com.library.domain.Author;
import com.library.domain.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Override
    List<Author> findAll();

    @Override
    Author save(Author author);


    @Override
    Optional<Author> findById(Long id);

    @Query(nativeQuery = true, value = "SELECT * from author A WHERE A.forename = FORENAME AND A.surname = SURNAME LIMIT 1;")
    Optional<Author> findAuthorByAuthorName(@Param("FORENAME") String forename, @Param("SURNAME") String surname);

    @Modifying
    @Query(nativeQuery = true , value = "INSERT INTO join_book_authors (book_id, author_id) values (BOOK_ID, AUTHOR_ID)")
    Integer save(@Param("BOOK_ID") Long book_id, @Param("AUTHOR_ID")Long author_id);

    @Override
    void deleteById(Long id);

    @Override
    long count();



}




