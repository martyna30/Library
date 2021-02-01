package com.library.repository;

import com.library.domain.bn.BnBook;
import org.hibernate.mapping.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BnRepository extends CrudRepository<BnBook, Long> {

    @Override
    List<BnBook> findAll();

    @Override
    Optional<BnBook> findById(Long id);

    @Override
    BnBook save(BnBook bnBook);

    @Override
    void deleteById(Long id);

    @Override
    long count();

    @Override
    <S extends BnBook> Iterable<S> saveAll(Iterable<S> entities);
}
