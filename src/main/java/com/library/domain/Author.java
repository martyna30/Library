package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*@NamedQuery(
         name = "Author.findIdByAuthorName",
         query = "FROM Author WHERE surname = :SURNAME AND forename = :FORENAME"

)*/
@NamedQueries({
        @NamedQuery(name="Author.findAuthorsByForename",
                query = "FROM Author WHERE forename LIKE CONCAT('%', :FORENAME, '%')"),
        @NamedQuery(name="Author.findAuthorsBySurname",
                query = "FROM Author WHERE surname LIKE CONCAT('%', :SURNAME, '%')"),
})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author {
    private Long id;
    private String surname;
    private String forename;

    private ObjectName objectNameAuthor;
    private List<Book> books = new ArrayList<>();

    private static Author author;

    public Author(Long id, String surname, String forename) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
    }
    public Author(String surname, String forename) {
        this.surname = surname;
        this.forename = forename;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @Column(name = "FORENAME")
    public String getForename() {
        return forename;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBJECT_NAME_AUTHOR_ID")
    public ObjectName getObjectNameAuthor() {
        return objectNameAuthor;
    }

    @ManyToMany (cascade = CascadeType.ALL,
            mappedBy = "authors"
    )
    public List<Book> getBooks() {
        return books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setObjectNameAuthor(ObjectName objectNameAuthor) {
        this.objectNameAuthor = objectNameAuthor;
    }

    public static Author getAuthor() {
        return author;
    }
}


