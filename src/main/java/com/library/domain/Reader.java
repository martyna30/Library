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
@Table(name = "READER")
public class Reader {
    private Long id;
    private String surname;
    private String forename;
    private List<Rental> borrowedBooks = new ArrayList<>();

    public Reader(String surname, String forename) {
        this.surname = surname;
        this.forename = forename;
    }

    public Reader(Long id, String forename, String surname) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "READER_ID", unique = true)
    public Long getId() {
        return id;
    }
    //@Column(name = "FORENAME")
    public String getForename() {
        return forename;
    }
    //@Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @OneToMany(
            targetEntity = Rental.class,//prawa strona relacji 1:n
            mappedBy = "reader",//obiekt po lewej 1 :n
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    public List<Rental> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBorrowedBooks(List<Rental> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
