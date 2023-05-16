package com.library.controller;

import com.library.domain.ObjectName;
import com.library.domain.ObjectNameDto;
import com.library.exception.BookNotFoundException;
import com.library.exception.ObjectNameNotFoundException;
import com.library.mapper.ObjectMapper;
import com.library.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidClassException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController //jest w stanie przyjmować oraz zwracać dane za pomocą żądań i odpowiedzi HTTP,
@RequestMapping("/v1/library")
public class ObjectControler {

    @Autowired
    ObjectService objectService;

    @Autowired
    ObjectMapper objectMapper;


    @GetMapping(value = "getObjectsWithSpecifiedTitleOrAuthor", consumes = APPLICATION_JSON_VALUE)
    public List<ObjectNameDto> getObjectsWithSpecifiedTitleOrAuthor(@RequestParam String objectToSearch) throws ObjectNameNotFoundException {
        return objectMapper.mapToObjectDtoList(objectService.getObjectsWithSpecifiedTitleOrAuthor(objectToSearch));

    }

    @GetMapping(value = "findObjectWithSpecifiedTitleOrAuthor", consumes = APPLICATION_JSON_VALUE)
    public ObjectNameDto getObjectWithSpecifiedTitleOrAuthor(@RequestParam String objectToSearch) throws ObjectNameNotFoundException {
        return objectMapper.mapToObjectDto(objectService.findObjectWithSpecifiedTitleOrAuthor(objectToSearch).orElseThrow(ObjectNameNotFoundException::new));
    }

    @PostMapping(value = "createObjectName", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<ObjectNameDto> createObjectName(@RequestBody ObjectNameDto objectNameDto) throws InvalidClassException {
        try {
            objectMapper.mapToObjectDto(objectService.save(objectMapper.mapToObject(objectNameDto)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (InvalidClassException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
