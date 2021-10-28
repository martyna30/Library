package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*@NamedQuery(
         name = "Author.findIdByAuthorName",
         query = "FROM Author WHERE surname = :SURNAME AND forename = :FORENAME"

)*/




@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author {
    private Long id;
    private String surname;
    private String forename;
    private List<Book> books = new ArrayList<>();

    public Author(String surname, String forename) {
        this.surname = surname;
        this.forename = forename;
    }

    public Author(Long id, String surname, String forename) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID", unique = true)
    public Long getId() {
        return id;
    }


    //@NotNull
    //@Size(min = 3, max = 30)
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    //@NotNull
    //@Size(min = 3, max = 30)
    @Column(name = "FORENAME")
    public String getForename() {
        return forename;
    }

    @ManyToMany(//
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

    //public Optional<Long> setId(Optional<Long> id1) {
        //return id1;
    //}
}
