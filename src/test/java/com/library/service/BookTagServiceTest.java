package com.library.service;



import com.library.domain.BookTag;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookTagServiceTest {

    @Autowired
    BookTagService bookTagService;

    @Test
    public void testSaveBookTag() {
        //Given
        BookTag fiction = new BookTag("fiction");

        //When
        bookTagService.saveBookTag(fiction);
        long id = fiction.getId();

        List<BookTag> bookTagsList = bookTagService.getAllBookTags();

        Optional<BookTag> bookTags = bookTagService.getBookTag(id);

        //Then
        Assert.assertTrue(bookTags.isPresent());
        Assert.assertNotNull(fiction);
        assertEquals(1, bookTagsList.size());

        //CleanUp
        bookTagService.deleteBookTag(id);
    }

    @Test
    public void testUpdateBookTag() {
        //Given
        BookTag fiction = new BookTag("fiction");

        bookTagService.saveBookTag(fiction);
        long id = fiction.getId();
        String literaryGenre = fiction.getLiteraryGenre();

        //When
        BookTag modified = bookTagService.getBookTag(id).orElse(null);
        modified.setLiteraryGenre("drama");

        bookTagService.saveBookTag(modified);
        String literaryGenre2 = modified.getLiteraryGenre();

        List<BookTag>bookTagList = bookTagService.getAllBookTags();

        //Then
        assertNotNull(modified);
        assertNotEquals(literaryGenre, literaryGenre2);
        assertEquals(1, bookTagList.size());

        //CleanUp
        bookTagService.deleteBookTag(id);

    }

    @Test
    public void testDeleteBookTag() {
        //Given
        BookTag fiction = new BookTag("fiction");

        bookTagService.saveBookTag(fiction);
        long id = fiction.getId();

        //When
        bookTagService.deleteBookTag(id);

        List<BookTag>bookTagList = bookTagService.getAllBookTags();

        //Then
        Assert.assertEquals(0, bookTagList.size());
    }

}
