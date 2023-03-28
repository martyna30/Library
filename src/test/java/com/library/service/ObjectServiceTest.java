package com.library.service;

import com.library.domain.Book;
import com.library.domain.ObjectName;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InvalidClassException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ObjectServiceTest {

    @Autowired
    ObjectService objectService;


    @Test
    public void testSaveObjectName() throws InvalidClassException {
        //Given
        Book makbet  = new Book("Makbet", "200098",1999,1);
        ObjectName objectName = new ObjectName("Makbet", makbet);

        //When
        objectService.save(objectName);
        long objectNameId = objectName.getId();

        //Then
        Assert.assertNotEquals(0, objectNameId);

        //CleanUp
        try {
            objectService.deleteObjectName(objectNameId);
        } catch (Exception e) {
            //nothing
        }
    }
    @Test
    public void testUpdateObjectName() throws InvalidClassException {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet", "200002", 1900, 1);
        ObjectName objectName = new ObjectName("Romeo and Juliet", romeoAndJuliet);

        objectService.save(objectName);
        long objectNameId = objectName.getId();

        //When
        ObjectName modified = objectService.getObjectName(objectNameId).orElse(null);
        modified.setName("Romeo");


        objectService.save(modified);

        //Then
        Assert.assertNotEquals(objectName.getName(), "Romeo");
        //CleanUp
        try {
          objectService.deleteObjectName(objectNameId);
        } catch (Exception e) {
            //nothing
        }
    }

    @Test
    public void deleteObjectName() throws InvalidClassException {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet", "200003", 1900, 1);
        ObjectName objectName = new ObjectName("Romeo and Juliet", romeoAndJuliet);

        objectService.save(objectName);
        long objectNameId = objectName.getId();
        List<ObjectName> objectNames = objectService.getAllObjectName();

        //When
        objectService.deleteObjectName(objectNameId);
        List<ObjectName> objectNamesAfterDeleted = objectService.getAllObjectName();

        //Then
        Assert.assertNotEquals(objectNames.size(), objectNamesAfterDeleted.size());

    }
}
