package com.library.repository;

import com.library.domain.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

    @Override
    List<Rental> findAll();

    @Override
    Optional<Rental> findById(final Long rentalId);

    @Override
    void deleteById(Long rentalId);

    @Override
    Rental save(Rental rental);

    @Override
    void deleteAll();


    Optional<Rental> findRentalByTitle(String title);


}
