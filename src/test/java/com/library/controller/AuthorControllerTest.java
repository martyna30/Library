package com.library.controller;

import com.library.domain.Author;
import com.library.domain.AuthorDto;
import com.library.domain.ListAuthorsDto;
import com.library.mapper.AuthorMapper;
import com.library.repository.AuthorRepository;
import com.library.security.PasswordEncoder;
import com.library.service.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {
    @InjectMocks
    AuthorController authorController;
    @Mock
    AuthorService authorService;
    @Mock
    AuthorMapper authorMapper;
    @Mock
    Errors errors;

    @Test
    public void testGetAuthors() throws Exception { //do poprawy
        //Given
        List<AuthorDto> listAuthorsDtos = new ArrayList<>();
        listAuthorsDtos.add(new AuthorDto());
        long count = 1;
        ResponseEntity.ok(new ListAuthorsDto(
                count,
                listAuthorsDtos));

        List<Author> listAuthors = new ArrayList<>();
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(authorService.getAllAuthors(pageRequest)).thenReturn(listAuthors);
        when(authorService.getCount()).thenReturn(count);
        when(authorMapper.mapToAuthorsDtoList(listAuthors)).thenReturn(listAuthorsDtos);

        //when
        ResponseEntity<ListAuthorsDto> responseEntity = authorController.getAuthors(1, 10);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(1, listAuthorsDtos.size());
        Assert.assertEquals(1, responseEntity.getBody().getAuthors().size());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

    }


    @Test
    public void testGetByForename() throws Exception {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        List<Author> listAuthor = new ArrayList<>();
        listAuthor.add(brzechwa);

        List<AuthorDto> listAuthorDto = new ArrayList<>();
        listAuthorDto.add(authorMapper.mapToAuthorDto(brzechwa));

        when(authorService.getAuthorsForenameWithSpecifiedCharacters(brzechwa.getForename())).thenReturn(listAuthor);
        when(authorMapper.mapToAuthorsDtoList(listAuthor)).thenReturn(listAuthorDto);

        //When
        List<AuthorDto> listAuthorWithForename = authorController.getAuthorsForenameWithSpecifiedCharacters(brzechwa.getForename());

        //then
        Assert.assertNotNull(listAuthorWithForename);
        Assert.assertEquals(1, listAuthorWithForename.size());

    }

    @Test
    public void testGetBySurname() throws Exception {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        List<Author> listAuthor = new ArrayList<>();
        listAuthor.add(brzechwa);

        List<AuthorDto> listAuthorDto = new ArrayList<>();
        listAuthorDto.add(authorMapper.mapToAuthorDto(brzechwa));

        when(authorService.getAuthorsSurnameWithSpecifiedCharacters(brzechwa.getSurname())).thenReturn(listAuthor);
        when(authorMapper.mapToAuthorsDtoList(listAuthor)).thenReturn(listAuthorDto);

        // When
        List<AuthorDto> listAuthorWithSurname = authorController.getAuthorsSurnameWithSpecifiedCharacters(brzechwa.getSurname());

        //then
        Assert.assertNotNull(listAuthorWithSurname);
        Assert.assertEquals(1, listAuthorWithSurname.size());
    }

    @Test
    public void testGetAuthor() throws Exception {
        //Given
        Author brzechwa = new Author("Brzechwa", "Jan");
        AuthorDto authorDto = new AuthorDto(null, "Brzechwa", "Jan");

        when(authorService.getAuthor(brzechwa.getId())).thenReturn(Optional.of(brzechwa));
        when(authorMapper.mapToAuthorDto(brzechwa)).thenReturn(authorDto);

        // When
        AuthorDto authorDto1 = authorController.getAuthor(brzechwa.getId());

        //then
        Assert.assertNotNull(authorDto1);
        Assert.assertEquals("Jan", authorDto1.getForename());

    }

    @Test
    public void testDeleteAuthor() throws Exception {
        //Given
        List<Author> listAuthorsEmpty = new ArrayList<>();
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Author brzechwa = new Author(1L, "Brzechwa", "Jan");
        List<Author> listAuthors = new ArrayList<>();
        listAuthors.add(brzechwa);

        //when
        authorController.deleteAuthor(brzechwa.getId());

        //then
        assertNotEquals(listAuthors.size(), listAuthorsEmpty.size());

    }


    @Test
    public void testUpdateAuthor() throws InvalidClassException {
        //Given
        Author brzechwa = new Author(1L, "Brzechwa", "Jan");
        brzechwa.setSurname("Mickiewicz");
        AuthorDto modified = new AuthorDto(brzechwa.getId(), brzechwa.getSurname(), brzechwa.getForename());

        //when
        ResponseEntity<Object> responseEntity = authorController.updateAuthor(modified,errors);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(modified);
        Assert.assertNotEquals(brzechwa.getSurname(), "Brzechwa");
        Assert.assertEquals(brzechwa.getSurname(), "Mickiewicz");

        Assert.assertEquals(modified.getSurname(), "Mickiewicz");
        Assert.assertEquals(brzechwa.getSurname(), modified.getSurname());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void creteAuthor() throws InvalidClassException {
        Author brzechwa = new Author(1L, "Brzechwa", "Jan");
        AuthorDto authordto = new AuthorDto(brzechwa.getId(), brzechwa.getSurname(), brzechwa.getForename());
        ResponseEntity.ok(authordto);

        //when
        ResponseEntity<Object> responseEntity = authorController.createAuthor(authordto, errors);

        //then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(authordto);
        Assert.assertEquals(brzechwa.getSurname(), authordto.getSurname());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findIdByName() throws InvalidClassException {
        //Given
        Author brzechwa = new Author(1L, "Brzechwa", "Jan");

        when(authorService.findAuthorByName(brzechwa.getForename(), brzechwa.getSurname())).thenReturn(Optional.of(brzechwa));

        //when
        Optional<Author> author = authorController.findIdByName(brzechwa.getForename(), brzechwa.getSurname());

        //then
        Assert.assertNotNull(author.get());
        Assert.assertNotNull(brzechwa);
        Assert.assertEquals(Optional.of(1L).get(), brzechwa.getId());
    }
}










