package com.library.repository;

import com.library.domain.BookTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query(nativeQuery = true, value = "SELECT * from booktag BT WHERE BT.literaryGenre = :GENRE LIMIT 1 ")
    Optional<BookTag> findBookTagByName(@Param("GENRE") String literaryGenre);

    @Override
    void deleteById(Long id);

    @Override
    long count();


}


