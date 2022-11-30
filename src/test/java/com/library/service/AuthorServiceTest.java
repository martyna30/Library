package com.library.service;

import com.library.domain.BookTag;
import org.junit.Assert;
import com.library.domain.Author;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorServiceTest {
    @Autowired
    AuthorService authorService;

    @Test
    public void testSaveAuthor() {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");

        //When
        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();

//        List<Author> authorsList = authorService.getAllAuthors();

        //Then
       // assertEquals(1, authorsList.size());
        assertNotEquals(0, authorId);

        //CleanUp
        authorService.deleteAuthor(authorId);
    }

    @Test
    public void testUpdateAuthor() {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");

        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();
        String surename = brzechwa.getSurname();
        String forename = brzechwa.getForename();

        //When
        Author modified = authorService.getAuthor(authorId).orElse(null);
        modified.setSurname("Tuwim");
        modified.setForename("Julian");

        authorService.saveAuthor(modified);
        String surename2 = modified.getSurname();
        String forename2 = modified.getForename();

//        List<Author> authors = authorService.getAllAuthors();

        //Then
        assertNotEquals(surename, surename2);
        assertNotEquals(forename, forename2);
      //  assertEquals(1, authors.size());

        //CleanUp
        authorService.deleteAuthor(authorId);
    }

    @Test
    public void testDeleteAuthor() {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");

        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();

        //when
        authorService.deleteAuthor(authorId);

//        List<Author> authors = authorService.getAllAuthors();

        //Then
        //assertEquals(0, authors.size());

    }
}
