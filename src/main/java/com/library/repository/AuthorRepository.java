package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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

    @Query(nativeQuery = true, value = "SELECT * from author A WHERE A.forename = :FORENAME AND A.surname = :SURNAME LIMIT 1")
    Optional<Author> findAuthorByName(@Param("FORENAME") String forename, @Param("SURNAME") String surname);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}




