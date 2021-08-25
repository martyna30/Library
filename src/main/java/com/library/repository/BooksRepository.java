package com.library.repository;


import com.library.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BooksRepository extends CrudRepository<Book, Long> {//. Wewnątrz ostrych nawiasów podajemy obiekt,
        // który będziemy pobierać //CrudRepository, która jest udostępniana w bibliotekach springframework i
        // udostępnia metody oraz logikę pobierania danych z bazy danych.
        @Override
        List<Book> findAll();

        //List<Book> findAllByYearOfPublicationBetween(1900, 1991);

        @Override
        Book save(Book book);

        @Override
        Optional<Book> findById(Long id);

        @Override
        void deleteById(Long id);

        @Override
        long count();

        @Query(nativeQuery = true, value = "SELECT * FROM book B WHERE B.signature = :SIGNATURE LIMIT 1")
        Optional<Book>findBySignature(@Param("SIGNATURE") String signature);

        /*@Query(nativeQuery = true)
        List<Book> retrieveBookWithParticularAuthor(@Param("FORENAME")String forename);*/

        //@Query
        //List<Book> retrieveBookWithParticularAuthor(@Param("FORENAME")String forename);

        //@Query
        //List<Book> retrieveBookWithParticularTitle(@Param("TITLE") String title );

        //@Query
        //List<Book> retrieveBookWithParticularGenre(@Param("LITERARY_GENRE") String literaryGenre);
}


