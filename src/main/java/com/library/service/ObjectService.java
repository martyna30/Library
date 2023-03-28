package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.ObjectName;

import com.library.repository.ObjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObjectService {
    @Autowired
    ObjectRepository objectRepository;



    public List<ObjectName> getObjectsWithSpecifiedTitleOrAuthor(String objectToSearch){
      List<ObjectName>objects = objectRepository.findAllByName(objectToSearch);
      if (objects.size() > 0) {
          objects.stream().collect(Collectors.toList());
      }
      return objects;

    }
    //@Transactional
    public ObjectName save(final ObjectName objectName) {
        Optional<ObjectName> optionalObjectName = objectRepository.findByName(
                objectName.getName());
        if (optionalObjectName.isPresent()) {
            objectName.setId(optionalObjectName.get().getId());
        }
        return objectRepository.save(objectName);
    }


    public Optional<ObjectName> findObjectWithSpecifiedTitleOrAuthor(String name) {
       return objectRepository.findByName(name);

    }

    public void deleteObjectName(final Long id) {
        objectRepository.deleteById(id);
    }


    public List<ObjectName> getAllObjectName() {
        return objectRepository.findAll();
    }

    public Optional<ObjectName> getObjectName(final Long id) {
        return objectRepository.findById(id);
    }

}
