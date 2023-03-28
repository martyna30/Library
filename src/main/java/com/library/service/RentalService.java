package com.library.service;

import com.library.domain.*;
import com.library.exception.MyException;
import com.library.exception.MyException2;
import com.library.facade.FacadeWatcher;
import com.library.mapper.BookMapper;
import com.library.mapper.LoggedUserMapper;
import com.library.mapper.UserMapper;
import com.library.repository.BooksRepository;
import com.library.repository.RentalRepository;
import com.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.io.InvalidClassException;
import java.time.LocalDate;
import java.util.*;



@Service
public class RentalService {
    private static Logger LOGGER = LoggerFactory.getLogger(RentalService.class);

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    BookService bookService;
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
    //@Transactional(dontRollbackOn = Exception.class )

    public void processRequest(BookDto bookDto, String username) {
        try {
            checkoutBook(bookDto, username);
        } catch (UnexpectedRollbackException e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LOGGER.info(e.getMessage());
        }
    }
    @Transactional()
    public boolean checkoutBook(BookDto bookDto, String username) throws UnexpectedRollbackException {
        Book book = bookMapper.mapToBook(bookDto);
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<String> titleExistForUser = userOptional.get()
                .getBorrowedBooks().stream().map(rental -> rental.getTitle())
                .filter(s -> s.equals(book.getTitle())).findFirst();
        long countBorrowed = 0;
        long newCountOfBorrowed = 0;

        int amountOfBook = book.getAmountOfBook();
        int newAmountOfBook = amountOfBook - 1;
        countBorrowed = book.getBorrowedBooks().stream().count();
        try {
            if (titleExistForUser.isPresent()) {
                Optional<Rental> rentalForUser = rentalRepository.findRentalByTitle(book.getTitle());
                if (rentalForUser.isPresent()) {

                    countBorrowed = rentalForUser.get().getAmountOfBorrowedBooks();
                    newCountOfBorrowed = countBorrowed + 1;
                    rentalForUser.get().setTitle(book.getTitle());
                    rentalForUser.get().setStartingDate(LocalDate.now());
                    rentalForUser.get().setFinishDate(LocalDate.now().plusDays(30));
                    rentalForUser.get().setAmountOfBorrowedBooks((int) newCountOfBorrowed);
                    rentalForUser.get().setStatus(Status.ACTIVE);
                    book.getBorrowedBooks().add(rentalForUser.get());
                    book.setAmountOfBook(newAmountOfBook);
                    userOptional.get().getBorrowedBooks().add(rentalForUser.get());
                    bookService.saveBook(book);
                    //newCountOfBorrowed = book.getBorrowedBooks().stream().map(rentals -> rentals.getAmountOfBorrowedBooks()).count();
                }
            } else {
                countBorrowed = book.getBorrowedBooks().stream().map(rental -> rental.getAmountOfBorrowedBooks()).count();
                newCountOfBorrowed = countBorrowed + 1;
                Rental rental = new Rental();
                rental.setTitle(book.getTitle());
                rental.setStartingDate(LocalDate.now());
                rental.setFinishDate(LocalDate.now().plusDays(30));
                rental.setAmountOfBorrowedBooks((int) newCountOfBorrowed);
                rental.setStatus(Status.ACTIVE);

                book.getBorrowedBooks().add(rental);
                book.setAmountOfBook(newAmountOfBook);
                userOptional.get().getBorrowedBooks().add(rental);
                bookService.saveBook(book);
               // newCountOfBorrowed = book.getBorrowedBooks().stream().map(rental1 -> rental1.getAmountOfBorrowedBooks()).count();
            }
        } catch (InvalidClassException e) {
            throw new RuntimeException(e);
        } catch (UnexpectedRollbackException e) {
            LOGGER.info(e.getMessage());
        }
        return newCountOfBorrowed > countBorrowed ? true : false;
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
