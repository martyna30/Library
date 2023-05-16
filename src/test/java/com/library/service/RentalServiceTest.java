package com.library.service;

import com.library.domain.*;
import com.library.exception.MyException2;
import com.library.mapper.BookMapper;
import com.library.mapper.LoggedUserMapper;
import com.library.mapper.UserMapper;
import com.library.repository.BooksRepository;
import com.library.repository.RentalRepository;
import com.library.repository.UserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.InvalidClassException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RentalServiceTest {

    @Autowired
    RentalService rentalService;

    @Autowired
    BookService bookService;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    UserService userService;

    @Autowired
    ObjectService objectService;

    @Test
    public void testSaveRental() throws InvalidClassException { //ok
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        Book jasImalgosia = new Book("Jaś i Małgosia","200002",1900, 1);
        //ObjectName objectName = new ObjectName("Jaś i Małgosia", jasImalgosia);
        //jasImalgosia.setObjectName(objectName);
        int amountOfBook = jasImalgosia.getAmountOfBook();
        int newAmountOfBook = amountOfBook - 1;

        userService.saveUser(user);

        Rental rental = new Rental();
        //rental.setUser(user);
        rental.setStatus(Status.ACTIVE);
        rental.setTitle("Jaś i Małgosia");
        rental.setAmountOfBorrowedBooks(1);
        rental.setBook(jasImalgosia);
        user.getBorrowedBooks().add(rental);
        //jasImalgosia.getBorrowedBooks().add(rental);
        jasImalgosia.setAmountOfBook(newAmountOfBook);


        //When
        bookService.saveBook(jasImalgosia);
        rentalService.saveRental(rental);


        List<Rental> rentals = rentalService.getRentals("Martyna");
        Optional<Rental> rentalId = rentalService.getRental(rental.getId());
        Optional<Book> jasimalgosiaId = bookService.getBook(jasImalgosia.getId());
        Optional<User> userId = userService.getUser(user.getId());
        //Then
        Assert.assertNotEquals(0, rentalId);
        Assert.assertNotEquals(0, jasimalgosiaId.get());
        //CleanUp
        try {

            rentalService.deleteRental(rental.getId());
            userService.deleteUser(user.getId());
            bookService.deleteBook(jasImalgosia.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveBookWithRental() throws InvalidClassException { //ok
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        Book jasImalgosia = new Book("Jaś i Małgosia","200002",1900, 1);
        ObjectName objectName = new ObjectName("Jaś i Małgosia", jasImalgosia);
        jasImalgosia.setObjectName(objectName);
        userService.saveUser(user);

        int amountOfBook = jasImalgosia.getAmountOfBook();
        int newAmountOfBook = amountOfBook - 1;   //0
        jasImalgosia.setAmountOfBook(newAmountOfBook);

        Rental rental = new Rental(
                jasImalgosia.getTitle(),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                1,
                Status.ACTIVE);
               //user);
        //When
        rental.setBook(jasImalgosia);
        user.getBorrowedBooks().add(rental);
        //jasImalgosia.getBorrowedBooks().add(rental);
        bookService.saveBook(jasImalgosia);
        rentalService.saveRental(rental);
        userService.saveUser(user);

        //long countNew = jasImalgosia.getBorrowedBooks().stream().
                //map(rental1 -> rental1.getAmountOfBorrowedBooks()).count();

        Optional<Rental> rentalId = rentalService.getRental(rental.getId());
        Optional<Book>jasimalgosiaId = bookService.getBook(jasImalgosia.getId());
        //Then
        Assert.assertNotEquals(Optional.of(0), rentalId);
        Assert.assertNotEquals(Optional.of(0), jasimalgosiaId);

         Assert.assertTrue(amountOfBook > newAmountOfBook);

        //CleanUp
        try {
            rentalService.deleteRental(rental.getId());
            userService.deleteUser(user.getId());
            bookService.deleteBook(jasImalgosia.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCheckoutBook() throws Exception { //ok
        //Given
        Book jasImalgosia = new Book("Jaś i Małgosia","200002",1900, 1);
        ObjectName objectName = new ObjectName("Jaś i Małgosia", jasImalgosia);
        jasImalgosia.setObjectName(objectName);
        bookService.saveBook(jasImalgosia);
        BookDto bookDto = bookMapper.mapToBookDto(jasImalgosia);
        String username = "Martyna";
        Optional<User> userId = userRepository.findByUsername("Martyna");
        //When

        boolean ischeckout = rentalService.checkoutBook(bookDto,username);

        Optional<Rental> rental = rentalRepository.findRentalByTitleAndUser(userId.get().getId(),bookDto.getTitle());
        //Then
        Assert.assertTrue(ischeckout);

        // List<Rental>rental = rentalRepository.findRentalForUser(userId.get().getId());


        //CleanUp
        rentalService.deleteRental(rental.get().getId());
        bookService.deleteBook(jasImalgosia.getId());
        //bookService.deleteBook(jasImalgosia.getId());
    }
    @Test
    public void testGetRentals() throws InvalidClassException { //ok
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        Book jasImalgosia = new Book("Jaś i Małgosia","200002",1900, 2);
        ObjectName objectName = new ObjectName("Jaś i Małgosia", jasImalgosia);
        jasImalgosia.setObjectName(objectName);
        //userService.saveUser(user);
        bookService.saveBook(jasImalgosia);

        Rental rental = new Rental();
        //rental.setUser(user);
        rental.setStatus(Status.ACTIVE);
        rental.setTitle("Jaś i Małgosia");
        rental.setAmountOfBorrowedBooks(1);
        rental.setBook(jasImalgosia);
        user.getBorrowedBooks().add(rental);
        //jasImalgosia.getBorrowedBooks().add(rental);
        userService.saveUser(user);
        rentalService.saveRental(rental);
        //When
        Optional<Rental> rentalId = rentalService.getRental(rental.getId());
        List<Rental> rentalsForUser = rentalService.getRentals(user.getUsername());
        List<Rental> rentals = rentalService.getAllRentals();
        //List<Rental> rentalsForBook =  jasImalgosia.getBorrowedBooks();
        //Then
        Assert.assertNotNull(rentalId);
        Assert.assertNotEquals(0, rentalsForUser.size());
        Assert.assertNotEquals(0, rentals.size());
        //Assert.assertNotEquals(0, rentalsForBook.size());
        //CleanUp
        try {
            rentalService.deleteRental(rental.getId());
            userService.deleteUser(user.getId());
            bookService.deleteBook(jasImalgosia.getId());


        } catch (Exception e) {
            //nothing
        }

    }

    @Test
    public void deleteRental() throws InvalidClassException {//
        //Given
        User user = new User("Maria", "123", "maria@gmail.com", "USER");
        Book las = new Book("Las","200009",1900, 1);
        ObjectName objectName = new ObjectName("Las", las);
        las.setObjectName(objectName);
        bookService.saveBook(las);

        Rental rental = new Rental();
        //rental.setUser(user);
        rental.setStatus(Status.ACTIVE);
        rental.setTitle("Las");
        rental.setAmountOfBorrowedBooks(1);
        rental.setBook(las);
        //las.getBorrowedBooks().add(rental);
        user.getBorrowedBooks().add(rental);

        userService.saveUser(user);
        rentalService.saveRental(rental);
        List<Rental> rentalslist = rentalService.getAllRentals();

        //When
        rentalService.deleteRental(rental.getId());
        userService.deleteUser(user.getId());
        bookService.deleteBook(las.getId());

        List<Rental> rentalsAfterDeleted = rentalService.getAllRentals();

        //Then
        Assert.assertNotEquals(rentalslist.size(),rentalsAfterDeleted.size());
    }
}

