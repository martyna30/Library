package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.repository.BooksRepository;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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
    //@Autowired
    //EntityManager entityManager;

    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactory;

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

        EntityManager em = entityManagerFactory.getObject().createEntityManager();
        Session session = (Session) em.getDelegate();
        try {
            Optional<Author> authorDetached = authorService.getIdByAuthorName(listForename.stream().findFirst().get(), listSurname.stream().findFirst().get());
            if(authorDetached.isPresent()) {
                Optional<Long> authorId = authorDetached.map(author -> author.getId());
                //book.getAuthors().stream().findAny().get().setId(authorId.get());

                //EntityManagerFactory emf = Persistence.createEntityManagerFactory("test"); // nie dziala bo ta nazwa nie jest przypadkowa, tylko musi byc gdzieś zdefiniowana
                //emf.createEntityManager().merge(book.getAuthors().get(0));

                Transaction transaction = session.beginTransaction();
                Author mergedAuthor = em.merge(authorDetached.get());
                mergedAuthor.setId(authorId.get());
                session.update(mergedAuthor);


                Book bookToDB = booksRepository.save(book);
                Long bookId = bookToDB.getId();
                authorService.saveIntoJoinTable(bookId, authorId.get()); // moze tu zmien n abookservice
                transaction.commit();

            }
            else {
                booksRepository.save(book);
            }
        } catch (DataIntegrityViolationException dE) {
            System.out.println("Entry already exist");
        } catch (PersistentObjectException pE) {
            System.out.println("Author already exist, but has added entry to join Table ");
       }
        catch(Exception ex) {
            System.out.println("Other bug");
        }
        finally {
            session.close();
            em.close();
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

