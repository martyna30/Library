package com.library.service;


import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.BookTag;
import com.library.domain.ObjectName;
import com.library.domain.bn.TypeOfObject;
import com.library.exception.ObjectNameNotFoundException;
import org.junit.Assert;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void testSaveBookWithObjectName() throws ObjectNameNotFoundException {
        Book makbet  = new Book();
        //Book kaczka_Dziwaczka = new Book("Kaczka Dziwaczka","197002" );
        //Book romeoAndJuliet = new Book("Romeo and Juliet", "196003");

        //makbet.set(new ObjectName(makbet.getId(), null, TypeOfObject.BOOK));

        bookService.saveBook(makbet);

        //long makbet_object_id = makbet.getObjectsName().getId();

        long makbetId = makbet.getId();

        Assert.assertNotEquals(0, makbetId);
        //Assert.assertNotEquals(0, makbet_object_id);

        //Assert.assertEquals(makbetId,makbet_object_id);

        //CleanUp
        try {
         //bookService.deleteBook(makbetId);
        } catch (Exception e) {
            //nothing
        }
    }



    @Test
    public void testSaveBookWithAuthorAndBookTag() {
        //Given
        BookTag fiction = new BookTag("fiction");
        BookTag thriller = new BookTag("thiller");
        BookTag fantasy = new BookTag("fantasy");

        Author brzechwa  = new Author("Brzechwa","Jan");
        Author pratchett = new Author("Pratchett","Terry");
        Author shakespeare = new Author("Shakespeare","William" );

        Book makbet  = new Book("Makbet", "195001");
        Book kaczka_Dziwaczka = new Book("Kaczka Dziwaczka","197002" );
        Book romeoAndJuliet = new Book("Romeo and Juliet", "196003");

        makbet.getAuthors().add(pratchett);
        makbet.getAuthors().add(shakespeare);
        kaczka_Dziwaczka.getAuthors().add(brzechwa);
        romeoAndJuliet.getAuthors().add(shakespeare);

        shakespeare.getBooks().add(romeoAndJuliet);
        shakespeare.getBooks().add(makbet);
        brzechwa.getBooks().add(kaczka_Dziwaczka);
        pratchett.getBooks().add(makbet);

        makbet.getBookTags().add(fiction);
        makbet.getBookTags().add(thriller);
        kaczka_Dziwaczka.getBookTags().add(fantasy);
        romeoAndJuliet.getBookTags().add(thriller);
        romeoAndJuliet.getBookTags().add(fiction);

        fiction.getBooks().add(makbet);
        fiction.getBooks().add(romeoAndJuliet);
        thriller.getBooks().add(makbet);
        thriller.getBooks().add(romeoAndJuliet);
        fantasy.getBooks().add(kaczka_Dziwaczka);

        //When
        //bookService.saveBook(makbet);
        //bookService.saveBook(kaczka_Dziwaczka);
        //bookService.saveBook(romeoAndJuliet);

        long book1Id = makbet.getId();
        long book2Id = kaczka_Dziwaczka.getId();
        long book3Id = romeoAndJuliet.getId();

        long bookTag1Id = makbet.getBookTags().get(0).getId();
        long author1Id = kaczka_Dziwaczka.getAuthors().get(0).getId();

       // List<Book> bookList = bookService.getAllBooks(1);

        //Then
        //Assert.assertEquals(3, bookList.size());

        Assert.assertNotEquals(0, book1Id);
        Assert.assertNotEquals(0, book2Id);
        Assert.assertNotEquals(0, book3Id);

        Assert.assertNotEquals(0, bookTag1Id);
        Assert.assertNotEquals(0, author1Id);

        //CleanUp
        try {
            bookService.deleteBook(book1Id);
            bookService.deleteBook(book2Id);
            bookService.deleteBook(book3Id);
        } catch (Exception e) {
            //nothing
        }
    }

    @Test
    public void testUpdateBookWithAuthorAndBookTag() {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet", "196003");


       // bookService.saveBook(romeoAndJuliet);
        String title = romeoAndJuliet.getTitle();
        String signature = romeoAndJuliet.getSignature();

        long bookId = romeoAndJuliet.getId();

        //When
        Book modified = bookService.getBook(bookId).orElse(null);
        modified.setTitle("Juliet");
        modified.setSignature("200001");

        //bookService.saveBook(modified);
        String title2 = modified.getTitle();
        String signature2 = modified.getSignature();

        //List<Book> bookList = bookService.getAllBooks(1,10);

        //Then
        assertNotNull(modified);
        Assert.assertNotEquals(romeoAndJuliet, modified);
        Assert.assertNotEquals(title, title2);
        Assert.assertNotEquals(signature, signature2);

       // Assert.assertEquals(1, bookList.size());

        //CleanUp
        bookService.deleteBook(bookId);
    }

    @Test
    public void deleteBook() {
        //Given
        Book romeoAndJuliet = new Book("Romeo and Juliet", "196003");

       // bookService.saveBook(romeoAndJuliet);
        long bookId = romeoAndJuliet.getId();

        //When
        bookService.deleteBook(bookId);

        //List<Book> bookList = bookService.getAllBooks(1,10);

        //Then
        //Assert.assertEquals(0, bookList.size());

    }


}


