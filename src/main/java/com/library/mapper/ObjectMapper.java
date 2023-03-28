package com.library.mapper;

import com.library.domain.ObjectNameDto;
import com.library.domain.ObjectName;
import org.springframework.stereotype.Component;

import java.io.InvalidClassException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Component
public class ObjectMapper {


    public List<ObjectNameDto> mapToObjectDtoList(final List<ObjectName> objectsWithSpecifiedTitleOrAuthor) {
        List<ObjectNameDto>objectDtos = objectsWithSpecifiedTitleOrAuthor.stream()
                .map(objectName -> new ObjectNameDto(
                        objectName.getName()))
                .collect(Collectors.toList());
        return objectDtos;
    }

    public ObjectNameDto mapToObjectDto(ObjectName objectName) {
        return new ObjectNameDto(
                objectName.getName()
        );
    }

    public ObjectName mapToObject(ObjectNameDto objectNameDto) throws InvalidClassException {
        return new ObjectName(
                objectNameDto.getName(),
                objectNameDto.getClass()

        );
    }

    public List<ObjectName> mapToObjectList(final List<ObjectNameDto> objectsWithSpecifiedTitleOrAuthor) {
        List<ObjectName>objects= objectsWithSpecifiedTitleOrAuthor.stream()
                .map(objectNamedto -> {
                    try {
                        return new ObjectName(
                                objectNamedto.getName(),
                                objectNamedto.getClass());
                    } catch (InvalidClassException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return objects;
    }
}
