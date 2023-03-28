package com.library.repository;

import com.library.domain.Book;
import com.library.domain.ObjectName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ObjectRepository extends CrudRepository<ObjectName, Long> {

    @Override
    List<ObjectName> findAll();


    Optional<ObjectName> findByName(String objectToSearch);

    @Override
    ObjectName save(ObjectName objectName);

    @Override
    void deleteById(Long id);

    List<ObjectName> findAllByName(String name);

    @Override
    Optional<ObjectName> findById(Long id);
}
