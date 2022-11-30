package com.library.service;

import com.library.domain.Book;
import com.library.domain.ObjectName;

import com.library.repository.ObjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ObjectName save(final ObjectName objectName) {
        return objectRepository.save(objectName);
    }




    public Optional<ObjectName> findObjectWithSpecifiedTitleOrAuthor(String name) {
       return objectRepository.findByName(name);

    }
}
