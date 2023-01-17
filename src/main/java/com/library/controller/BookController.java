package com.library.controller;


import com.library.domain.BookDto;
import com.library.domain.ListBookDto;
import com.library.exception.BookNotFoundException;
import com.library.exception.ObjectNameNotFoundException;
import com.library.validationGroup.OrderChecks;
import com.library.validationGroup.OrderChecks2;
import com.library.mapper.BookMapper;
import com.library.repository.BooksRepository;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.InvalidClassException;
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

    @GetMapping
   // ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "getBooks")
    public ResponseEntity<ListBookDto> getBooks(@RequestParam int page, @RequestParam int size) {
        //@PathVariable("sortDir") String sortDir,
        //@PathVariable("sort") String sort) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(
                new ListBookDto(
                        bookService.getCount(),
                        bookMapper.mapToBookDtoList(bookService.getAllBooks(pageRequest))
                )
        );
    }
    @RequestMapping(method = RequestMethod.GET, value = "getBooksWithSpecifiedTitle")
    public List<BookDto>getBooksWithSpecifiedTitle(@RequestParam String title) {
        return bookMapper.mapToBookDtoList(bookService.getBooksWithSpecifiedTitle(title));
    }




    @RequestMapping(method = RequestMethod.GET, value = "getBook")
    public BookDto getBook(@RequestParam Long bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDto(bookService.getBook(bookId).orElseThrow(BookNotFoundException::new));
    }

    @DeleteMapping( value = "deleteBook", consumes = APPLICATION_JSON_VALUE)
    //@RequestMapping(method = RequestMethod.DELETE, value = "deleteBook", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?>deleteBook(@RequestParam Long bookId) {
        try{
            bookService.deleteBook(bookId);
            System.out.println(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBook")
    public ResponseEntity<Object>updateBook(@Validated(value = {OrderChecks2.class}) @Valid @RequestBody BookDto bookDto, Errors errors) throws ObjectNameNotFoundException, InvalidClassException {

        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        bookMapper.mapToBookDto(bookService.saveBook(bookMapper.mapToBook(bookDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBook", consumes = APPLICATION_JSON_VALUE)
//consumes do danych wejsciowych zadania
    public ResponseEntity<Object> createBook(@Validated(value = {OrderChecks.class}) @Valid @RequestBody BookDto bookDto
            , Errors errors) throws ObjectNameNotFoundException, InvalidClassException {

        if (errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if (!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

            bookService.saveBook(bookMapper.mapToBook(bookDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }











}






