package com.library.repository;

import com.library.domain.BookTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookTagRepository extends CrudRepository<BookTag, Long> {
    @Override
    List<BookTag> findAll();

    @Override
    BookTag save(BookTag bookTag);

    @Override
    Optional<BookTag> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}


