package com.library.controller;

import com.library.domain.ObjectName;
import com.library.domain.ObjectNameDto;
import com.library.exception.ObjectNameNotFoundException;
import com.library.mapper.ObjectMapper;
import com.library.repository.ObjectRepository;
import com.library.service.ObjectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ObjectControllerTest {
    @InjectMocks
    ObjectControler objectControler;
    @Mock
    ObjectService objectService;

    @Mock
    ObjectMapper objectMapper;

    @Test
    public void testGetObjectsWithSpecifiedTitleOrAuthor() throws ObjectNameNotFoundException {
        //Given
        List<ObjectName> objectNameList = new ArrayList<>();
        ObjectName objectName = new ObjectName();
        String objectToSearch = "test";
        objectName.setName(objectToSearch);
        objectNameList.add(objectName);
        List<ObjectNameDto> objectNameDtoList = new ArrayList<>();
        ObjectNameDto objectNameDto = new ObjectNameDto();
        objectNameDto.setName(objectToSearch);
        objectNameDtoList.add(objectNameDto);

        when(objectService.getObjectsWithSpecifiedTitleOrAuthor(objectToSearch)).thenReturn(objectNameList);
        when(objectMapper.mapToObjectDtoList(objectNameList)).thenReturn(objectNameDtoList);

        //When
        List<ObjectNameDto> objectNameResultList = objectControler.getObjectsWithSpecifiedTitleOrAuthor(objectToSearch);

        //Then
        Assert.assertNotNull(objectNameResultList);
        Assert.assertEquals(1, objectNameResultList.size());
        Assert.assertEquals(1, objectNameDtoList.size());
        Assert.assertEquals(objectNameResultList.size(), objectNameDtoList.size());
        Assert.assertTrue(objectNameDtoList.equals(objectNameResultList));
    }

    @Test
    public void testGetObjectWithSpecifiedTitleOrAuthor() throws ObjectNameNotFoundException {
        //Given
        ObjectName objectName = new ObjectName();
        String objectToSearch = "test";
        objectName.setName(objectToSearch);

        ObjectNameDto objectNameDto = new ObjectNameDto();
        objectNameDto.setName(objectToSearch);

        when(objectService.findObjectWithSpecifiedTitleOrAuthor(objectToSearch)).thenReturn(Optional.of(objectName));
        when(objectMapper.mapToObjectDto(objectName)).thenReturn(objectNameDto);

        //When
        ObjectNameDto resultObjectName = objectControler.getObjectWithSpecifiedTitleOrAuthor(objectToSearch);

        //Then
        Assert.assertNotNull(resultObjectName);
        Assert.assertEquals("test", resultObjectName.getName());
        Assert.assertEquals(objectNameDto.getName(), resultObjectName.getName());
        Assert.assertTrue(objectNameDto.equals(resultObjectName));
    }

    @Test
    public void testCreateObjectName() throws InvalidClassException {
        //Given
        ObjectName objectName = new ObjectName();
        String objectToSearch = "test";
        objectName.setName(objectToSearch);

        ObjectNameDto objectNameDto = new ObjectNameDto();
        objectNameDto.setName(objectToSearch);
        //when
        ResponseEntity<ObjectNameDto> responseEntity = objectControler.createObjectName(objectNameDto);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }
}







