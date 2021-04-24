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
@Table(name = "BOOK_TAG")
public class BookTag {
    private Long id;
    private String literaryGenre;
    private List<Book> books = new ArrayList<>();

    public BookTag(Long id, String literaryGenre) {
        this.id = id;
        this.literaryGenre = literaryGenre;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BOOK_TAG_ID", unique = true)
    public Long getId() {
        return id;
    }

    @NotNull
    @Column(name = "LITERARY_GENRE")
    public String getLiteraryGenre() {
        return literaryGenre;
    }

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name ="JOIN_BOOK_TAGS",
            joinColumns ={@JoinColumn(name = "BOOK_TAG_ID", referencedColumnName = "BOOK_TAG_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID")}
    )
    public List<Book> getBooks() {
        return books;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setLiteraryGenre(String literaryGenre) {
        this.literaryGenre = literaryGenre;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
