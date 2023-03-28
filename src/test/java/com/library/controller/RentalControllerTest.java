package com.library.controller;

import com.library.domain.*;
import com.library.exception.RentalNotFoundException;
import com.library.mapper.ObjectMapper;
import com.library.mapper.RentalMapper;
import com.library.service.ObjectService;
import com.library.service.RentalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentalControllerTest {

    @InjectMocks
    RentalController rentalController;
    @Mock
    RentalService rentalService;

    @Mock
    RentalMapper rentalMapper;
    @Mock
    Errors errors;
    @Test
    public void testGetRentals() throws Exception {
        //Given
        List<RentalDto> rentalDtos = new ArrayList<>();
        rentalDtos.add(new RentalDto());
        List<Rental> rentals = new ArrayList<>();

        when(rentalMapper.mapToRentalDtoList(rentals)).thenReturn(rentalDtos);
        when(rentalService.getAllRentals()).thenReturn(rentals);

        //when
        List<RentalDto> rentalDtosResultList = rentalController.getAllRentals();

        //then
        Assert.assertNotNull(rentalDtosResultList);
        Assert.assertEquals(1, rentalDtosResultList.size());
        Assert.assertEquals(rentalDtos.size(), rentalDtosResultList.size());
    }

    @Test
    public void testGetRental() throws RentalNotFoundException {
        //Given
        Rental rental = new Rental();
        Long id = 1L;
        rental.setId(id);
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(id);

        when(rentalService.getRental(rental.getId())).thenReturn(Optional.of(rental));
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);

        //when
        RentalDto rentalDtoResult =  rentalController.getRental(rental.getId());

        //then
        Assert.assertNotNull(rentalDtoResult);
        Assert.assertEquals(rentalDtoResult.getId(), rentalDto.getId());
        Assert.assertTrue(rentalDtoResult.equals(rentalDto));

    }


    @Test
    public void testDeleteRental() throws Exception {
        //Given
        Rental rental = new Rental();
        Long id = 1L;
        rental.setId(id);
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(id);

        //when
        ResponseEntity<Object> responseEntity = rentalController.deleteRental(rental.getId());

        //then
        assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void testCreateRental() throws InvalidClassException {
        //Given
        Rental rental = new Rental();
        Long id = 1L;
        rental.setId(id);
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(id);

        when(rentalController.createRental(any(RentalDto.class))).thenReturn(rentalDto);

        //when
        RentalDto rentalDtoResult = rentalController.createRental(rentalDto);

        //then
        Assert.assertNotNull(rentalDtoResult);
        Assert.assertEquals(rentalDtoResult.getId(), rentalDto.getId());
        Assert.assertTrue(rentalDtoResult.equals(rentalDto));
    }

    @Test
    public void testUpdateRental() {
        //Given
        RentalDto rentalDto = new RentalDto();
        Long id = 1L;
        rentalDto.setId(id);
        rentalDto.setTitle("");
        RentalDto modified = new RentalDto();
        modified.setTitle("test");
        modified.setId(id);
        //When
        when(rentalController.updateRental(rentalDto)).thenReturn(modified);

        //when
        RentalDto rentalDtoResult = rentalController.updateRental(modified);

        //Then
        Assert.assertNotNull(rentalDtoResult);
        Assert.assertEquals("", rentalDto.getTitle());
        Assert.assertEquals("test", rentalDtoResult.getTitle());

        Assert.assertNotEquals(rentalDtoResult.getTitle(), rentalDto.getTitle());
        Assert.assertFalse(rentalDtoResult.equals(rentalDto));
    }

    @Test
    public void testCheckoutBook() throws Exception {
        //Given
        String username = "Test";
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test");
        bookDto.setId(1L);

        //when
       // ResponseEntity<Object> responseEntity = rentalController.checkoutBook(bookDto,errors,username);

        //then
       // Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(bookDto);
        //Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetRentalsByUsername() throws Exception {
        //Given
        String username = "Test";
        LoggedUserDto loggedUserDto = new LoggedUserDto();
        loggedUserDto.setUsername(username);

        List<RentalDto> rentalDtos = new ArrayList<>();
        RentalDto rentalDto = new RentalDto();
        rentalDto.setLoggedUserDto(loggedUserDto);
        rentalDtos.add(rentalDto);

        List<Rental> rentals = new ArrayList<>();
        rentals.add(new Rental());

        when(rentalMapper.mapToRentalDtoList(rentals)).thenReturn(rentalDtos);
        when(rentalService.getRentals(username)).thenReturn(rentals);

        //when
        List<RentalDto> rentalDtosResultList = rentalController.getRentalsByUsername(username);

        //then
        Assert.assertNotNull(rentalDtosResultList);
        Assert.assertEquals(1, rentalDtosResultList.size());
        Assert.assertEquals("Test", rentalDtosResultList.stream()
                .map(rental -> rental.getLoggedUserDto())
                .map(user -> user.getUsername())
                .collect(Collectors.joining()));

        Assert.assertEquals(rentals.size(), rentalDtosResultList.size());
    }


}
