package com.library.controller;


import com.library.domain.AuthorDto;
import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.domain.BookTagDto;
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
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<Object> createBook(@Valid @RequestBody BookDto bookDto,BindingResult bindingResult) {

        /*Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations()
                    .stream()
                    .sorted(Comparator.comparing(ConstraintViolation::getMessageTemplate))
                    .collect(Collectors.toCollection(LinkedHashSet::new));*/

        if (bindingResult.hasErrors()) {
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
        }
        bookService.saveBook(bookMapper.mapToBook(bookDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


            /*Map<String, String> errorsMap = new HashMap<>();
            errorsMap.entrySet().stream().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = ((FieldError) error).getDefaultMessage();
                        errorsMap.put(fieldName, errorMessage).*/
        //ex.stream().sorted(Comparator.comparing(ConstraintViolation::getMessageTemplate)).collect(Collectors.toCollection(LinkedHashSet::new));

            /*for (FieldError error : errors.getFieldErrors()) {
                if (!responseErrors.containsKey(error.getField())) {
                    responseErrors.put(error.getField(), error.getDefaultMessage());
                }
            }*/




            /*
          Optional<String> firstErrorForField = errors.entrySet()
                  .stream()
                  .map(Map.Entry::getValue)
                  .flatMap(fieldErrors -> fieldErrors.stream())
                  .findFirst();

            if (firstErrorForField.isPresent()) {
                String error = firstErrorForField.get();
                List<String> firstErrorsList = new ArrayList<>();
                firstErrorsList.add(error);
                return new ResponseEntity<>(firstErrorsList, HttpStatus.BAD_REQUEST);
            }*/




    }




    /* tutaj jest zwracana tablica błędów w rodzaju:
                0	"Length title of the book must be beetween 3 and 30 characters"
                1	"prosze podac tytuł"
                2	"Must być z dużej litery i posiadac same litery"

                tylko skąd później frontend ma wiedzieć którego pola to dotyczy?

                to powinien być obiekt w rodzaju
                {
                    "title": [
                        "Length title of the book must be beetween 3 and 30 characters",
                        "prosze podac tytuł",
                        "Must być z dużej litery i posiadac same litery"
                    }
                }
                albo zlepione
                {
                    "title": "Length title of the book must be beetween 3 and 30 characters. Proszę podać tytuł. Must być z dużej litery i posiadac same litery."
                }

                albo tylko pierwszy błąd tego pola\
                {
                    "title": "Length title of the book must be beetween 3 and 30 characters."
                }
           */





