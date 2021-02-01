package com.library.repository;

import com.library.domain.Book;
import com.library.domain.bn.BnBook;
import org.springframework.data.repository.CrudRepository;
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

        @Override
        Book save(Book book);

        @Override
        Optional<Book> findById(Long id);

        @Override
        void deleteById(Long id);

        @Override
        long count();

        @Override
        <S extends Book> Iterable<S> saveAll(Iterable<S> entities);
}


