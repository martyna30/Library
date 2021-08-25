package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.sql.ast.Clause.LIMIT;
import static org.springframework.http.HttpHeaders.FROM;


/*
@NamedNativeQuery(
        name = "Book.retrieveBookWithParticularAuthor",
        query = "SELECT * FROM BOOK AS B " +
                "WHERE EXISTS( " +
                "SELECT * FROM JOIN_BOOK_AUTHORS AS BookAuthor JOIN AUTHOR AS A ON BookAuthor.AUTHOR_ID = A.AUTHOR_ID " +
                "WHERE BookAuthor.BOOK_ID = B.BOOK_ID AND A.forename LIKE :FORENAME LIMIT 1)",
        resultClass = Book.class
)*/
/*/@NamedQueries({

        @NamedQuery(
        name = "Book.retrieveBookWithParticularAuthor",
        query = "FROM BOOK AS B " +
                    "WHERE EXISTS( " +
                        "FROM JOIN_BOOK_AUTHORS AS BookAuthor JOIN AUTHOR AS A ON BookAuthor.AUTHOR_ID = A.AUTHOR_ID " +
                        "WHERE BookAuthor.BOOK_ID = B.BOOK_ID AND A.forename LIKE :FORENAME LIMIT 1)"
        ),
        @NamedQuery(
                name = "Book.retrieveBookWithParticularTitle",
                        query = "FROM BOOK WHERE title LIKE :TITLE LIMIT 1"
        ),

        @NamedQuery(
                name = "Book.retrieveBookWithParticularGenre",
                query = "FROM book AS B " +
                            "WHERE EXISTS( " +
                                "FROM join_book_tags AS BookTags JOIN book_tag AS Tag ON BookTags.book_tag_id = Tag.book_tag_id" +
                                " WHERE BookTags.book_id = B.book_id AND Tag.literary_genre LIKE :LITERARY_GENRE )"
        )

})*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book {
    private Long id;
    private String title;
    private int yearOfPublication;
    private String signature;
    private int amountOfbook;
    private int amountOfborrowed;
    private List<BookTag> bookTags = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    public Book(String title, String signature) {
        this.title = title;
        this.signature = signature;
    }

    public Book(Long id, String title, int yearOfPublication, String signature, List<BookTag> bookTagsList, List<Author> authorsList) {
        this.id = id;
        this.title = title;
        this.yearOfPublication = yearOfPublication;
        this.signature = signature;
        this.bookTags = bookTagsList;
        this.authors = authorsList;
    }

    public Book(String title, int yearOfPublication, String signature, List<BookTag> bookTagsList, List<Author> authorsList) {
        this.title = title;
        this.yearOfPublication = yearOfPublication;
        this.signature = signature;
        this.bookTags = bookTagsList;
        this.authors = authorsList;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    @Column
    public int getYearOfPublication() {
        return yearOfPublication; // na localdate?
    }


    @Column(name = "SIGNATURE", unique = true)
    public String getSignature() {
        return signature;
    }

    ///lazy pobieramy dane dopiero wtedy, gdy ich potrzebujemy.
    // gdy użyjemy gettera na powiązanej kolekcji, Hibernate wykonuje zapytanie do bazy danych.
    //@NotNull()
    //@NotBlank(message = "Literary genre must not be empty")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(

            name ="JOIN_BOOK_TAGS",
            joinColumns ={@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BOOK_TAG_ID", referencedColumnName = "BOOK_TAG_ID")}
    )
    public List<BookTag> getBookTags() {
        return bookTags;
    }

    //@NotNull
    //@NotBlank(message = "Book's author must not be empty")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})  //, CascadeType.PERSIST
        @JoinTable(
            name ="JOIN_BOOK_AUTHORS",
            joinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns ={@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")}
    )
    public List<Author> getAuthors() {
        return authors;
    }

    public int getAmountOfbook() {
        return amountOfbook;
    }

    public int getAmountOfborrowed() {
        return amountOfborrowed;
    }

    public void setAmountOfbook(int amountOfbook) {
        this.amountOfbook = amountOfbook;
    }

    public void setAmountOfborrowed(int amountOfborrowed) {
        this.amountOfborrowed = amountOfborrowed;
    }

    public void setId(Long id) { //nie nalezy zmieniac id i udostepniac setera
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setBookTags(List<BookTag> bookTags) {
        this.bookTags = bookTags;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
