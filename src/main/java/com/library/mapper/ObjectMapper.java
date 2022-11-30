package com.library.mapper;

import com.library.domain.ObjectNameDto;
import com.library.domain.ObjectName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Component
public class ObjectMapper {


    public List<ObjectNameDto> mapToObjectDtoList(final List<ObjectName> objectsWithSpecifiedTitleOrAuthor) {
        List<ObjectNameDto>objectDtos = objectsWithSpecifiedTitleOrAuthor.stream()
                .map(objectName -> new ObjectNameDto(
                        objectName.getId(),
                        objectName.getName()))
                .collect(Collectors.toList());
        return objectDtos;
    }

    public ObjectNameDto mapToObjectDto(ObjectName objectName) {
        return new ObjectNameDto(
                objectName.getId(),
                objectName.getName()
        );
    }

    public ObjectName mapToObject(ObjectNameDto objectNameDto) {
        return new ObjectName(
                objectNameDto.getId(),
                objectNameDto.getName()
                );
    }

    public List<ObjectName> mapToObjectList(final List<ObjectNameDto> objectsWithSpecifiedTitleOrAuthor) {
        List<ObjectName>objects= objectsWithSpecifiedTitleOrAuthor.stream()
                .map(objectNamedto -> new ObjectName(
                        objectNamedto.getId(),
                        objectNamedto.getName()))
                .collect(Collectors.toList());
        return objects;
    }
}
