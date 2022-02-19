package com.library.service;

import com.library.domain.Rental;
import com.library.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

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


}
