package com.library.controller;


import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.exception.ApiError;
import com.library.exception.BookNotFoundException;
import com.library.exception.ValidationHandler;
import com.library.mapper.BookMapper;
import com.library.repository.BooksRepository;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBook", consumes = APPLICATION_JSON_VALUE )
    public void deleteBook(@RequestParam Long bookId) {
        bookService.deleteBook(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBook")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return bookMapper.mapToBookDto(bookService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBook", consumes = APPLICATION_JSON_VALUE)//consumes do danych wejsciowych zadania
    public ResponseEntity<Object> createBook(@Valid @RequestBody BookDto bookDto, Errors errors) {
       if(errors.hasErrors()) {
           List<String>validateErrors = errors
                   .getFieldErrors()
                   .stream()
                   .map(e->e.getDefaultMessage())
                   .collect(Collectors.toList());
           LOGGER.info("validation error list: " + validateErrors);
           return new ResponseEntity<>(validateErrors, HttpStatus.BAD_REQUEST);
       }
        bookService.saveBook(bookMapper.mapToBook(bookDto));

       return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
