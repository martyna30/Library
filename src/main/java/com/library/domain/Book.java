package com.library.domain;

import com.library.domain.bn.TypeOfObject;
import com.library.validationGroup.Format;
import com.library.validationGroup.NotEmptyGroup;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private int amountOfBook;

    private ObjectName objectName;

    private List<BookTag> bookTags = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();
   // private List<Rental> borrowedBooks = new ArrayList<>();

    public Book(Long id,String title, int yearOfPublication, int amountOfBook, String signature, List<BookTag> bookTags,List<Author> authors) {
        this.id = id;
        this.title = title;
        this.signature = signature;
        this.yearOfPublication = yearOfPublication;
        this.amountOfBook = amountOfBook;
        this.bookTags = bookTags;
        this.authors = authors;
    }


    public Book(String title,String signature, int yearOfPublication, int amountOfBook) {
        this.title = title;
        this.signature = signature;
        this.yearOfPublication = yearOfPublication;
        this.amountOfBook = amountOfBook;

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
    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "OBJECT_NAME_ID", unique = true) //
    public ObjectName getObjectName() {
      return objectName;
    }

    //@LazyCollection(LazyCollectionOption.FALSE)
    //@OneToMany(
          //  cascade = {CascadeType.ALL})
   // @JoinColumn(name = "BOOK_ID")
   // public List<Rental> getBorrowedBooks() {
       // return borrowedBooks;
  //  }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinTable(
            name ="JOIN_BOOK_TAGS",
            joinColumns ={@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BOOK_TAG_ID", referencedColumnName = "BOOK_TAG_ID")}
    )
    public List<BookTag> getBookTags() {
        return bookTags;
    }
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
        @JoinTable(
            name ="JOIN_BOOK_AUTHORS",
            joinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")},
            inverseJoinColumns ={@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")}
    )
    public List<Author> getAuthors() {
        return authors;
    }

    public int getAmountOfBook() {
        return amountOfBook;
    }

    public void setAmountOfBook(int amountOfBook) {
        this.amountOfBook = amountOfBook;
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

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    public void setBookTags(List<BookTag> bookTags) {
        this.bookTags = bookTags;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }


}
