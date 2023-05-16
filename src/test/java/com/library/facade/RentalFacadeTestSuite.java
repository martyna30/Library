package com.library.facade;

import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.domain.ObjectName;
import com.library.domain.Rental;
import com.library.exception.MyException2;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import com.library.service.RentalService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RentalFacadeTestSuite {
    @InjectMocks
    RentalFacade rentalFacade;
    @Mock
    RentalService rentalService;
    @Mock
    BookService bookService;
    @Mock
    BookMapper bookMapper;

    @Test
    public void testProcessCheckoutBook() throws Exception {
        //Given
        Book jasImalgosia = new Book("Jaś i Małgosia","200002",1900, 1);
        ObjectName objectName = new ObjectName("Jaś i Małgosia", jasImalgosia);
        jasImalgosia.setObjectName(objectName);
        Rental rental = new Rental();
        rental.setTitle("Jaś i Małgosia");
        //jasImalgosia.getBorrowedBooks().add(rental);
        BookDto bookDto = new BookDto();
        String username = "Martyna";
        boolean wasCheckout = true;
        List<Rental> rentalsForUser = new ArrayList<>();
        rentalsForUser.add(rental);

        when(bookService.saveBook(jasImalgosia)).thenReturn(jasImalgosia);
        when(bookMapper.mapToBookDto(jasImalgosia)).thenReturn(bookDto);
        when(rentalService.checkoutBook(bookDto,username)).thenReturn(wasCheckout);
        when(rentalService.getRentals(username)).thenReturn(rentalsForUser);
        //When
        List<Rental> rentals = rentalFacade.processCheckoutBook(bookDto,username);
        //Then
        Assert.assertEquals(1, rentals.size());
        Assert.assertNotNull(rentals);
    }
}
