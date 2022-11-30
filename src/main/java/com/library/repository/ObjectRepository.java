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


    List<ObjectName> findAllByName(String objectToSearch);


    Optional<ObjectName> findByName(String objectToSearch);

    @Override
    ObjectName save(ObjectName objectName);




}
