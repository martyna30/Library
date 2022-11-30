package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Reader;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RENTAL")
public class Rental {
    private Long id;
    private LocalDate startingDate;
    private LocalDate finishDate;
    private int amountOfBorrowedBooks;
    @Enumerated(EnumType.STRING)
    private Status status;

    User user;

    Book book;

    public Rental(LocalDate startingDate, LocalDate finishDate, int amountOfBorrowedBooks, Status status) {
        this.startingDate = startingDate;
        this.finishDate = finishDate;
        this.amountOfBorrowedBooks = amountOfBorrowedBooks;
    }

    public Rental(Long id, LocalDate startingDate, LocalDate finishDate, Status status, int amountOfBorrowedBooks) {
        this.id = id;
        this.startingDate = LocalDate.now();
        this.finishDate = finishDate;
        this.status = status;
        this.amountOfBorrowedBooks = amountOfBorrowedBooks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "RENTAL_ID", unique = true)
    public Long getId() {
        return id;
    }
    //@Column(name = "STARTING_DATE")
    public LocalDate getStartingDate() {
        return startingDate;
    }
    //@Column(name = "FINISH_DATE")
    public LocalDate getFinishDate() {
        return finishDate;
    }
   // @Column(name = "AMOUNT_OF_BORROWED_BOOKS")


    @Column(name = "AMOUNT_OF_BORROWED")
    public int getAmountOfBorrowedBooks() {
        return amountOfBorrowedBooks;
    }



    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setAmountOfBorrowedBooks(int amountOfBorrowedBooks) {
        this.amountOfBorrowedBooks = amountOfBorrowedBooks;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
