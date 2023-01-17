package com.library.service;

import com.library.domain.*;
import com.library.exception.BookNotFoundException;
import com.library.mapper.LoggedUserMapper;
import com.library.mapper.UserMapper;
import com.library.repository.BooksRepository;
import com.library.repository.RentalRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    BooksRepository booksRepository;

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
    public List<Rental> checkoutBook(Long bookId, LoggedUserDto loggedUserDto) {
        Optional<Book> bookOptional = booksRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findByUsername(loggedUserMapper.mapToUser(loggedUserDto).getUsername());
        try {
            if (bookOptional.isPresent()) {
                int amountOfBook = bookOptional.get().getAmountOfBook();
                if (amountOfBook > 0) {
                    int newAmountOfBook = amountOfBook - 1;
                    bookOptional.get().setAmountOfBook(newAmountOfBook);

                    bookOptional.get().getBorrowedBooks()
                            .add(new Rental(
                                    bookOptional.get().getTitle(),
                                    LocalDate.now(),
                                    LocalDate.now().plusDays(30),
                                            1,
                                    Status.ACTIVE,
                                    userOptional.isPresent() ? userOptional.get() :
                                            new User(loggedUserMapper.mapToUser(loggedUserDto).getUsername()),
                                            bookOptional.get()
                                    )
                            );
                    booksRepository.save(bookOptional.get());
                } else {
                    System.out.println("Amount of books must be at least 1");
                    return null;
                }
            } else {
                System.out.println("Book doesn't exist");
            }
        } catch (BookNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return bookOptional.get().getBorrowedBooks();
    }


    public List<Rental> getRentals(LoggedUserDto loggedUserDto) {
        List<Rental> rentals = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(loggedUserMapper.mapToUser(loggedUserDto).getUsername());
        if (userOptional.isPresent()) {
            rentals = userOptional.get().getBorrowedBooks();
        } else {
            System.out.println("User doesn't exist");
        }
        return rentals;
    }
}
