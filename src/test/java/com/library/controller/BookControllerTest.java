package com.library.controller;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.domain.ListBookDto;
import com.library.exception.ObjectNameNotFoundException;
import com.library.mapper.AuthorMapper;
import com.library.mapper.BookMapper;
import com.library.service.AuthorService;
import com.library.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Mock
    BookMapper bookMapper;

    @Mock
    Errors errors;

    @Test
    public void testGetBooks() throws Exception {
        //Given
        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(new BookDto());
        long count = 1;
        List<Book> books = new ArrayList<>();
        int page = 1;
        int size = 10;

        when(bookService.getCount()).thenReturn(count);
        when(bookMapper.mapToBookDtoList(books)).thenReturn(booksDto);

        //when
        ResponseEntity<ListBookDto> responseEntity = bookController.getBooks(page, size);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(1, booksDto.size());
        Assert.assertEquals(1, responseEntity.getBody().getBooks().size());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetBooksWithSpecifiedTitle() {
        //Given
        BookDto bookDto = new BookDto();
        String title = "test";
        bookDto.setTitle(title);
        List<Book> books = new ArrayList<>();
        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(bookDto);

        when(bookMapper.mapToBookDtoList(books)).thenReturn(booksDto);

        //when
        List<BookDto> resultlist = bookController.getBooksWithSpecifiedTitle(title);

        //then
        Assert.assertNotNull(resultlist);
        Assert.assertEquals(1, booksDto.size());
        Assert.assertEquals(1, resultlist.size());
        Assert.assertTrue(booksDto.equals(resultlist));
    }

    @Test
    public void testGetBook() {
        //Given
        Book book = new Book();
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);

        when(bookService.getBook(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
        //when
        BookDto bookDtos = bookController.getBook(bookId);

        //then
        Assert.assertNotNull(bookDtos);
        Assert.assertEquals(bookId, bookDtos.getId());
        Assert.assertTrue(bookDtos.getId().equals(bookDto.getId()));

    }

    @Test
    public void testDeleteBook() throws Exception {
        //Given
        Book romeoAndJuliet = new Book();
        romeoAndJuliet.setId(1L);

        //when
        ResponseEntity<?> responseEntity = bookController.deleteBook(romeoAndJuliet.getId());

        //then
        assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateBook() throws InvalidClassException, Exception, ObjectNameNotFoundException {
        //Given
        BookDto modified = new BookDto();
        modified.setTitle("Test");

        //when
        ResponseEntity<Object> responseEntity = bookController.updateBook(modified, errors);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(modified);
        Assert.assertEquals("Test", modified.getTitle());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void creteBook() throws InvalidClassException, ObjectNameNotFoundException {
        //Given
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test");
        bookDto.setId(1L);

        Book book = new Book();
        book.setTitle("Test");
        book.setId(1L);

        //When
        ResponseEntity<Object> responseEntity = bookController.createBook(bookDto, errors);

        //Then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(bookDto);

        Assert.assertEquals("Test", bookDto.getTitle());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }
}


