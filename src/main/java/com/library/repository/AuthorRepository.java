package com.library.repository;

import com.library.domain.Author;
import com.library.domain.Book;
import org.springframework.data.repository.CrudRepository;
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

    @Override
    void deleteById(Long id);

    @Override
    long count();
}

