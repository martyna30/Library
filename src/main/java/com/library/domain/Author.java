package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
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

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "AUTHOR_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public String getSurname() {
        return surname;
    }

    @Column
    public String getForename() {
        return forename;
    }


    @ManyToMany(cascade = CascadeType.ALL)
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
