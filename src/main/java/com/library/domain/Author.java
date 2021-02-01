package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "AUTHOR_ID", unique = true)
    public Long getId() {
        return id;
    }

    @NotNull
    @Column
    public String getSurname() {
        return surname;
    }

    @NotNull
    @Column
    public String getForename() {
        return forename;
    }


    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name ="JOIN_BOOK_AUTHORS",
            joinColumns ={@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")}
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
}
