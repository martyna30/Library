package com.library.service;


import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.BookTag;
import com.library.domain.ObjectName;
import com.library.domain.bn.TypeOfObject;
import com.library.exception.ObjectNameNotFoundException;
import org.junit.Assert;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookTagService bookTagService;

    @Autowired
    AuthorService authorService;

    @Autowired
    ObjectService objectService;

    @Test
    public void testSaveBookWithObjectName() throws InvalidClassException {
        //Given
        Book makbet  = new Book("Makbet", "200098",1999,1);
        //Author shakespeare = new Author("Shakespeare","William" );
        ObjectName objectName = new ObjectName("Makbet", makbet);
        //When
        makbet.setObjectName(objectName);
        Book book = bookService.saveBook(makbet);
        long makbetId = book.getId();


        //Then
        Assert.assertNotEquals(0, makbetId);

        //CleanUp
        try {
         bookService.deleteBook(makbetId);
        } catch (Exception e) {
            //nothing
        }


    }

    @Test
    public void testSaveBookWithBookTag() throws InvalidClassException {
        //Given
        BookTag fantasy = new BookTag("fantasy");

        Book kaczka_Dziwaczka = new Book("Kaczka Dziwaczka","200004",2000,3);
        ObjectName objectName = new ObjectName("Kaczka Dziwaczka", kaczka_Dziwaczka);
        kaczka_Dziwaczka.setObjectName(objectName);

        kaczka_Dziwaczka.getBookTags().add(fantasy);
        fantasy.getBooks().add(kaczka_Dziwaczka);

        //When
        bookService.saveBook(kaczka_Dziwaczka);
        long book1Id = kaczka_Dziwaczka.getId();
        long objectNameBookId = kaczka_Dziwaczka.getObjectName().getId();

        long booktag1id = fantasy.getId();

        Assert.assertNotEquals(0, book1Id);
        Assert.assertNotEquals(0, objectNameBookId);
        Assert.assertNotEquals(0, booktag1id);

        //CleanUp
        try {
            bookService.deleteBook(book1Id);
            bookTagService.deleteBookTag(booktag1id);

        } catch (Exception e) {
            //nothing
        }
    }


    @Test
    public void testSaveBookWithAuthorAndBookTag() throws InvalidClassException {
        //Given
        BookTag fantasy = new BookTag("fantasy");

        Author brzechwa  = new Author("Brzechwa","Jan");
        ObjectName objectNameAuthor = new ObjectName("Jan Brzechwa", brzechwa);
        brzechwa.setObjectNameAuthor(objectNameAuthor);

        Book kaczka_Dziwaczka = new Book("Kaczka Dziwaczka","200004",2000,3);
        ObjectName objectName = new ObjectName("Kaczka Dziwaczka", kaczka_Dziwaczka);
        kaczka_Dziwaczka.setObjectName(objectName);

        kaczka_Dziwaczka.getBookTags().add(fantasy);

        fantasy.getBooks().add(kaczka_Dziwaczka);

        kaczka_Dziwaczka.getAuthors().add(brzechwa);

        brzechwa.getBooks().add(kaczka_Dziwaczka);

        //When
        bookService.saveBook(kaczka_Dziwaczka);
        long book1Id = kaczka_Dziwaczka.getId();
        long objectNameBookId = kaczka_Dziwaczka.getObjectName().getId();

        long booktag1id = fantasy.getId();

        long author1Id = brzechwa.getId();
        long objectNameAuthorId = brzechwa.getObjectNameAuthor().getId();


        //Then
        Assert.assertNotEquals(0, book1Id);
        Assert.assertNotEquals(0, objectNameBookId);

        Assert.assertNotEquals(0, booktag1id);


        //Assert.assertNotEquals(0, bookTag1Id);
        Assert.assertNotEquals(0, author1Id);
        Assert.assertNotEquals(0, objectNameAuthorId);

        //CleanUp
        try {
            //bookService.deleteBook(book1Id);
            bookService.deleteBook(book1Id);
            authorService.deleteAuthor(author1Id);
            bookTagService.deleteBookTag(booktag1id);
        } catch (Exception e) {
            //nothing
        }
    }


    @Test
    public void testUpdateBookWithObjectName() throws InvalidClassException {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet","200002",1900, 1);
        ObjectName objectName = new ObjectName("Romeo and Juliet", romeoAndJuliet);
        romeoAndJuliet.setObjectName(objectName);

        String title = romeoAndJuliet.getTitle();
        String signature = romeoAndJuliet.getSignature();

        bookService.saveBook(romeoAndJuliet);
        long bookId = romeoAndJuliet.getId();

        //When
        Book modified = bookService.getBook(bookId).orElse(null);
        modified.setTitle("Juliet");
        modified.setSignature("200001");
        modified.getObjectName().setName("Juliet");


        bookService.saveBook(modified);
        String title2 = modified.getTitle();
        String signature2 = modified.getSignature();
        //Then


        Assert.assertNotEquals(objectName.getName(),"Juliet");
        Assert.assertNotEquals(title, title2);
        Assert.assertNotEquals(signature, signature2);

        //CleanUp
        try {
           bookService.deleteBook(bookId);
        }
        catch (Exception e) {
            //nothing
        }
    }

    @Test
    public void deleteBookWithObjectName() throws InvalidClassException {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet", "200003", 1900, 1);
        ObjectName objectName = new ObjectName("Romeo and Juliet", romeoAndJuliet);
        romeoAndJuliet.setObjectName(objectName);

        bookService.saveBook(romeoAndJuliet);
        long bookId = romeoAndJuliet.getId();

        //When
        List<Book> books = bookService.getAllBooks(Pageable.unpaged());
        List<ObjectName> objectNames = objectService.getAllObjectName();

        try {
            bookService.deleteBook(bookId);
        } catch (Exception e) {
            //nothing
        }
        List<Book> booksAfterDelete = bookService.getAllBooks(Pageable.unpaged());
        List<ObjectName> objectNamesAfterDeleted = objectService.getAllObjectName();


        //Then
        Assert.assertNotEquals(books.size(), booksAfterDelete.size());
        Assert.assertNotEquals(objectNames.size(), objectNamesAfterDeleted.size());

    }
}


