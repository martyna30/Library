package com.library.domain;


import com.library.domain.registration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;



@NamedQuery(
        name = "User.retrieveUserWithRental",
        query = "FROM User WHERE  USER_ID LIKE : BOOK_ID AND USER_ID LIKE: USER_ID"
)



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String role;


    private boolean locked = false;


    private boolean enabled = false;

    private List<Rental> borrowedBooks = new ArrayList<>();

    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();
    public User(String username,String password, String email,String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column
    public String getUsername() {
        return username;
    }
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "USER_ID")
    public List<Rental> getBorrowedBooks() {
       return borrowedBooks;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            targetEntity = ConfirmationToken.class,//prawa strona relacji 1:n
            mappedBy = "user",//obiekt po lewej 1 :n
            cascade = {CascadeType.ALL}
    )
    public List<ConfirmationToken> getConfirmationTokens() {
        return confirmationTokens;
    }

    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }


    @Column(name = "LOCKED")
    public boolean isLocked() {
        return locked;
    }

    @Column(name = "ENABLED")
    public boolean isEnabled() {
        return enabled;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
        this.confirmationTokens = confirmationTokens;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setBorrowedBooks(List<Rental> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}



