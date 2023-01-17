package com.library.domain;


import com.library.domain.registration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Long id;
    @NotNull
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    private String role;

    @Column(name = "LOCKED")
    private boolean locked = false;

    @Column(name = "ENABLED")
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

    @OneToMany(
            targetEntity = Rental.class,//prawa strona relacji 1:n
            mappedBy = "user",//obiekt po lewej 1 :n
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    public List<Rental> getBorrowedBooks() {
        return borrowedBooks;
    }


    @OneToMany(
            targetEntity = ConfirmationToken.class,//prawa strona relacji 1:n
            mappedBy = "user",//obiekt po lewej 1 :n
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    public List<ConfirmationToken> getConfirmationTokens() {
        return confirmationTokens;
    }




    /*@Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
             return Arrays.stream(role.name().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }*/
    //@Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBorrowedBooks(List<Rental> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
        this.confirmationTokens = confirmationTokens;
    }


}



