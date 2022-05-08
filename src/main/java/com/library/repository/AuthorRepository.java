package com.library.repository;

import com.library.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>,
        PagingAndSortingRepository<Author, Long> {
    @Override
    Page<Author> findAll(Pageable pageable);

    @Override
    Author save(Author author);

    @Override
    Optional<Author> findById(Long id);

    @Query(nativeQuery = true, value = "SELECT * from author A WHERE A.forename = :FORENAME AND A.surname = :SURNAME LIMIT 1")
    Optional<Author> findAuthorByName(@Param("FORENAME") String forename, @Param("SURNAME") String surname);

    @Query
    List<Author>findAuthorsByForename(@Param("FORENAME") String forename);

    @Query
    List<Author>findAuthorsBySurname(@Param("SURNAME") String surname);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}




