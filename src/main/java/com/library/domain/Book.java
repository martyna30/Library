package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book {
    private Long id;
    private String title;
    private int yearOfPublication;
    private String signature;
    private List<BookTag> bookTags = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    public Book(String title, String signature) {
        this.title = title;
        this.signature = signature;
    }

    @Id
    @GeneratedValue
    @NonNull
    @Column(name = "BOOK_ID", unique = true)
    public Long getId() {
        return id;
    }

    @NotBlank
    @Size(min=3)
    @Column
    public String getTitle() {
        return title;
    }

    @Column
    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @NotBlank
    @Size(min=1)
    @Column
    public String getSignature() {
        return signature;
    }

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "books", fetch = FetchType.LAZY)
    ///lazy pobieramy dane dopiero wtedy, gdy ich potrzebujemy.
    // gdy użyjemy gettera na powiązanej kolekcji, Hibernate wykonuje zapytanie do bazy danych.
    public List<BookTag> getBookTag() {
        return bookTags;
    }

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "books", fetch = FetchType.LAZY)
    public List<Author> getAuthors() {
        return authors;
    }

    public void setId(Long id) {
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

    public void setBookTag(List<BookTag> bookTags) {
        this.bookTags = bookTags;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
