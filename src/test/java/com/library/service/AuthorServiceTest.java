package com.library.service;

import com.library.domain.Author;
import com.library.domain.ObjectName;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InvalidClassException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorServiceTest {
    @Autowired
    AuthorService authorService;


    @Test
    public void testSaveAuthor() throws InvalidClassException {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        //When
        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();
        long objectNameId = brzechwa.getObjectNameAuthor().getId();
        Optional<Author> authorBrzechwa = authorService.getAuthor(authorId);

        //Then
        assertNotEquals(0, authorId);
        assertNotEquals(0, objectNameId);
        assertNotEquals(0, authorBrzechwa.get());
        //CleanUp
        authorService.deleteAuthor(authorId);
    }

    @Test
    public void testSaveAuthorWithObjectName() throws InvalidClassException {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        ObjectName objectNameAuthor = new ObjectName("Jan Brzechwa", brzechwa);
        brzechwa.setObjectNameAuthor(objectNameAuthor);

        //When
        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();
        Optional<Author> authorBrzechwa = authorService.getAuthor(authorId);
        long objectNameId = brzechwa.getObjectNameAuthor().getId();


        //Then
        assertNotEquals(0, authorId);
        assertNotEquals(0, objectNameId);
        assertNotEquals(0, authorBrzechwa.get());
        //CleanUp
        authorService.deleteAuthor(authorId);
    }

    @Test
    public void getAuthor() throws InvalidClassException {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        ObjectName objectNameAuthor = new ObjectName("Jan Brzechwa", brzechwa);
        brzechwa.setObjectNameAuthor(objectNameAuthor);
        authorService.saveAuthor(brzechwa);
        //When
        Optional<Author>author = authorService.findAuthorByName(brzechwa.getForename(), brzechwa.getSurname());

        //Then
        assertNotEquals(0, author.get());
        assertEquals(brzechwa.getSurname(),author.get().getSurname());

        //CleanUp
        authorService.deleteAuthor(brzechwa.getId());
    }

    @Test
    public void getAuthorsWithSpecifiedCharacters() throws InvalidClassException { ///do sprawdzenia na czerwono potem userservice
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        ObjectName objectNameAuthor = new ObjectName("Jan Brzechwa", brzechwa);
        brzechwa.setObjectNameAuthor(objectNameAuthor);
        authorService.saveAuthor(brzechwa);
        //When
        List<Author>authors =  authorService.getAuthorsForenameWithSpecifiedCharacters(brzechwa.getForename());
        List<Author>authorsList =  authorService.getAuthorsSurnameWithSpecifiedCharacters(brzechwa.getSurname());

        String surname = authorsList.stream().map(author -> author.getSurname()).filter(s -> s.equals("Brzechwa")).collect(Collectors.joining());
       String forename = String.valueOf(authors.stream().map(author -> author.getForename()).filter(f->f.equals("Jan")).findFirst().get());

        //Then
        assertNotEquals(0, authorsList.size());
        assertNotEquals(0, authors.size());

        assertEquals(brzechwa.getSurname(), surname);
        assertEquals(brzechwa.getForename(), forename);

        //CleanUp
        authorService.deleteAuthor(brzechwa.getId());

    }

    @Test
    public void testUpdateAuthorWithObjectName() throws InvalidClassException {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        ObjectName objectName = new ObjectName("Jan Brzechwa", brzechwa);
        brzechwa.setObjectNameAuthor(objectName);

        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();
        String surename = brzechwa.getSurname();
        String forename = brzechwa.getForename();

        //When
        Author modified = authorService.getAuthor(authorId).orElse(null);
        modified.setSurname("Tuwim");
        modified.setForename("Julian");
        modified.getObjectNameAuthor().setName("Julian Tuwim");

        authorService.saveAuthor(modified);
        String surename2 = modified.getSurname();
        String forename2 = modified.getForename();

        //Then
        Assert.assertNotEquals(objectName.getName(),"Julian Tuwim");
        assertNotEquals(surename, surename2);
        assertNotEquals(forename, forename2);

        //CleanUp
        authorService.deleteAuthor(authorId);
    }

    @Test
    public void testDeleteAuthor() throws InvalidClassException {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");

        authorService.saveAuthor(brzechwa);
        long authorId = brzechwa.getId();
        List<Author> authors = authorService.getAllAuthors(Pageable.unpaged());

        //When
        authorService.deleteAuthor(authorId);

        List<Author> authorsAfterDeleted = authorService.getAllAuthors(Pageable.unpaged());

        //Then
        assertNotEquals(authors.size(), authorsAfterDeleted.size());

    }
}
