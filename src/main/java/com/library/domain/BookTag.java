package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@NamedQuery(
        name = "BookTag.findByLiteraryGenre",
        query = "FROM BookTag WHERE literaryGenre LIKE CONCAT('%', :GENRE, '%')"
)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BOOK_TAG")
public class BookTag {
    private Long id;
    private String literaryGenre;
    private List<Book> books = new ArrayList<>();

    public BookTag(String literaryGenre) {
        this.literaryGenre = literaryGenre;
    }

    public BookTag(Long id, String literaryGenre) {
        this.id = id;
        this.literaryGenre = literaryGenre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_TAG_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "LITERARY_GENRE")
    public String getLiteraryGenre() {
        return literaryGenre;
    }

    @ManyToMany (fetch = FetchType.EAGER,
            mappedBy = "bookTags"
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
