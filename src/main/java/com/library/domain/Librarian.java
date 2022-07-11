package com.library.domain;

import com.library.domain.model.Librarians;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LIBRARIAN")
public class Librarian implements Librarians {

    private Long id;
    private String surname;
    private String forename;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIBRARIAN_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "FORENAME")
    public String getForename() {
        return forename;
    }

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
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
}
