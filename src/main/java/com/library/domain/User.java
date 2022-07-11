package com.library.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;

    //private String cardNumber;

    private boolean active;

    private List<Rental> borrowedBooks = new ArrayList<>();



    public User(Long id, String username,String password, String role, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.role = role;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    public Long getId() {
        return id;
    }

    @OneToMany(
            targetEntity = Rental.class,//prawa strona relacji 1:n
            mappedBy = "user",//obiekt po lewej 1 :n
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    public List<Rental> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setBorrowedBooks(List<Rental> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }



    public boolean isActive() {
        return active;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public void setActive(boolean active) {
        this.active = active;
    }
}



