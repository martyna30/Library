package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;









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
                name = "Book.",
                query = "FROM BOOK WHERE title LIKE :TITLE LIMIT 100"
            ),

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

@NamedQuery(
        name = "Book.findByTitle",
        query = "FROM Book WHERE title LIKE CONCAT('%', :TITLE, '%')"
)
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
    //private int amountOfborrowed;

    //private ObjectName objectName;
    private List<BookTag> bookTags = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
    private List<Rental> borrowedBooks = new ArrayList<>();

    public Book(Long id,String title, int yearOfPublication, String signature, List<BookTag> bookTags,List<Author> authors) {
        this.id = id;
        this.title = title;
        this.signature = signature;
        this.yearOfPublication = yearOfPublication;
        this.bookTags = bookTags;
        this.authors = authors;
        //this.objectName = objectName;
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

    @Column()
    public int getYearOfPublication() {
        return yearOfPublication; // na localdate?
    }


    @Column(name = "SIGNATURE", unique = true)
    public String getSignature() {
        return signature;
    }


    /*@OneToOne(cascade = {CascadeType.ALL },fetch = FetchType.EAGER)
    @JoinColumn(name = "OBJECT_NAME_BOOK_ID")
    public ObjectName getObjectsName() {
        return objectName;
    }*/


    ///lazy pobieramy dane dopiero wtedy, gdy ich potrzebujemy.
    // gdy użyjemy gettera na powiązanej kolekcji, Hibernate wykonuje zapytanie do bazy danych.
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(

            name ="JOIN_BOOK_TAGS",
            joinColumns ={@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BOOK_TAG_ID", referencedColumnName = "BOOK_TAG_ID")}
    )
    public List<BookTag> getBookTags() {
        return bookTags;
    }

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
        @JoinTable(
            name ="JOIN_BOOK_AUTHORS",
            joinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns ={@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")}
    )
    public List<Author> getAuthors() {
        return authors;
    }
    @OneToMany(
            targetEntity = Rental.class,//prawa strona relacji 1:n
            mappedBy = "book",//obiekt po lewej 1 :n
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    public List<Rental> getBorrowedBooks() {
        return borrowedBooks;
    }


    public int getAmountOfbook() {
        return amountOfbook;
    }

    public void setAmountOfbook(int amountOfbook) {
        this.amountOfbook = amountOfbook;
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

    public void setBorrowedBooks(List<Rental> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }




}
