package com.library.controller;


import com.library.domain.BookDto;
import com.library.exception.BookNotFoundException;
import com.library.exception.OrderChecks;
import com.library.mapper.BookMapper;
import com.library.repository.BooksRepository;
import com.library.service.BookService;
import com.library.validation.SignatureConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController //jest w stanie przyjmować oraz zwracać dane za pomocą żądań i odpowiedzi HTTP,
@RequestMapping("/v1/library") //, adnotacja aby określić adres, pod którym Controller będzie przyjmował żądania,
// oraz nad metodami, aby określić adresy i typy żądań, na które metody Controllera będą reagować.
public class BookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BookMapper bookMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getBooks")
    public List<BookDto> getBooks() {
        return bookMapper.mapToBookDtoList(bookService.getAllBooks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBook")
    public BookDto getBook(@RequestParam Long bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDto(bookService.getBook(bookId).orElseThrow(BookNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBook", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?>deleteBook(@RequestParam Long bookId) {
        try{
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBook")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookMapper.mapToBookDto(bookService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBook", consumes = APPLICATION_JSON_VALUE)
//consumes do danych wejsciowych zadania
    public ResponseEntity<Object> createBook(@Validated(value = {OrderChecks.class}) @Valid @RequestBody BookDto bookDto
            , Errors errors) {

        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            });
           /* String keySignature = signatureConstraint.field();
            if (signatureConstraint.field() != null && !errorsMap.containsKey(keySignature)) {
                errorsMap.put(keySignature, new ArrayList<>());
                errorsMap.get(keySignature).add(signatureConstraint.message());*/

                errorsMap.values().stream().findFirst();



                //for (List<String> fieldErrors: errorsMap.values()) {
                //fieldErrors.stream().findFirst();

                /*for (int i = 0; i < fieldErrors.size(); i++) {
                    fieldErrors.set(i, fieldErrors.get(i).substring(2));
                }*/
                return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);

        }

            bookService.saveBook(bookMapper.mapToBook(bookDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }


      /*if (bindingResult.hasErrors()) {
            Map<String, List<String>> errorsMap = new HashMap<>();

            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                String key = fieldError.getField();

                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }

                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }

            for (List<String> fieldErrors: errorsMap.values()) {
                fieldErrors.sort(String::compareTo);

                for (int i = 0; i < fieldErrors.size(); i++) {
                    fieldErrors.set(i, fieldErrors.get(i).substring(2));
                }
            }

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }*/
    }






