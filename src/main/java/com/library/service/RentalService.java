package com.library.service;

import com.library.domain.*;
import com.library.domain.rental.RentalBookDto;
import com.library.exception.BookNotFoundException;
import com.library.mapper.BookMapper;
import com.library.mapper.LoggedUserMapper;
import com.library.mapper.UserMapper;
import com.library.repository.BooksRepository;
import com.library.repository.RentalRepository;
import com.library.repository.UserRepository;
import com.library.validationGroup.OrderChecks3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LoggedUserMapper loggedUserMapper;


    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRental(final Long rentalId) {
        return rentalRepository.findById(rentalId);
    }

    public void deleteRental(Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }


    @Transactional
    public boolean checkoutBook (BookDto bookDto, String username) {
        //Optional<Book> bookOptional = booksRepository.findById(bookId);
        Book book = bookMapper.mapToBook(bookDto);
        Optional<User> userOptional = userRepository.findByUsername(username);
        long count = 0;
        long countNew = 0;
        try {
            int amountOfBook = book.getAmountOfBook();
                // if (amountOfBook > 0) {
                    int newAmountOfBook = amountOfBook - 1;
                    book.setAmountOfBook(newAmountOfBook);
                    count = book.getBorrowedBooks().stream().count();
                    book.getBorrowedBooks()
                            .add(new Rental(
                                    book.getTitle(),
                                    LocalDate.now(),
                                    LocalDate.now().plusDays(30),
                                            1,
                                    Status.ACTIVE,
                                    userOptional.isPresent() ? userOptional.get() :
                                            new User(userOptional.get().getUsername()),
                                    book
                                    )
                            );
                    booksRepository.save(book);
            countNew = book.getBorrowedBooks().stream().count();
        } catch (BookNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return countNew > count ? true : false;
    }


    public List<Rental> getRentals(String username) {
        List<Rental> rentals = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            rentals = userOptional.get().getBorrowedBooks();
        } else {
            System.out.println("User doesn't exist");
        }
        return rentals;
    }
}
