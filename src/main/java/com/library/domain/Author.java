package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @Column(name = "FORENAME")
    public String getForename() {
        return forename;
    }

    @ManyToMany(mappedBy = "authors")
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
