package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.repository.BooksRepository;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BooksRepository booksRepository;

    @Autowired
    AuthorService authorService;

    public List<Book> getAllBooks() {
        return booksRepository.findAll();

    }

    @Transactional
    public Book saveBook(final Book book) {
        List<String>listForename = book.getAuthors().stream()
                .map(Author::getForename).collect(Collectors.toList());

        List<String>listSurname = book.getAuthors().stream()
                .map(Author::getSurname).collect(Collectors.toList());
        try {
            Optional<Long> idAuthor = authorService.getIdByAuthorName(listForename.stream().findFirst().get(), listSurname.stream().findFirst().get());
            if(idAuthor.isPresent()) {
                book.getAuthors().stream().findAny().get().setId(idAuthor.get());

                //EntityManagerFactory emf = Persistence.createEntityManagerFactory("test"); // nie dziala bo ta nazwa nie jest przypadkowa, tylko musi byc gdzieś zdefiniowana
                //emf.createEntityManager().merge(book.getAuthors().get(0));

               // EntityManager em = Jpa.createEntityManager();

                //Optional<Author> update = authorService.getAuthor(idAuthor.get());
                //update.get().setForename(listForename.stream().findFirst().get());
                //update.get().setSurname(listForename.stream().findFirst().get());
                //book.getAuthors().add(update.get());
                //update.get().getBooks().add(book);
                Book bookToDB = booksRepository.save(book);
                Long bookId = bookToDB.getId();
                authorService.saveIntoJoinTable(bookId, idAuthor.get());
            }
            else {
                booksRepository.save(book);
            }
        } catch (DataIntegrityViolationException dE) {
            System.out.println("Entry already exist");
        } catch (PersistentObjectException pE) {
            System.out.println("Author already exist, but has added entry to join Table ");
       }
        return book;
    }

    public Optional<Book> getBook(final Long id) {
        return booksRepository.findById(id);
    }

    public void deleteBook(final Long id) {
        booksRepository.deleteById(id);
    }


}

