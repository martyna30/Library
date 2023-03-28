package com.library.facade;

import com.library.domain.BookDto;
import com.library.domain.Rental;
import com.library.exception.MyException2;
import com.library.service.RentalService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalFacade {

    private static Logger LOGGER = LoggerFactory.getLogger(RentalFacade.class);


    @Autowired
    RentalService rentalService;


    public List<Rental> processCheckoutBook(BookDto bookDto, String username) throws Exception {

        boolean wasCheckout = rentalService.checkoutBook(bookDto, username);

        if(!wasCheckout) {
            LOGGER.error(RentalProcessingException.ERR_BOOK_NOT_FOUND);
            throw new RentalProcessingException(RentalProcessingException.ERR_BOOK_NOT_FOUND);
        }
        LOGGER.info("Book has been checkout");

        List<Rental> rentals = rentalService.getRentals(username);
        if(rentals.size() == 0) {
            LOGGER.error(RentalProcessingException.ERR_LIST_OF_BORROWED_BOOKS_FOR_USER_NOT_FOUND);
            throw new RentalProcessingException(RentalProcessingException.ERR_LIST_OF_BORROWED_BOOKS_FOR_USER_NOT_FOUND);
        }
        LOGGER.info("List of borrowed books for user");
        return rentals;
    }
}
