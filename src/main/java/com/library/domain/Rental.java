package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Reader;
import java.time.LocalDate;


/*@NamedQuery(
        name = "Rental.retrieveRentalWithBookIdAndUserId",
        query = "FROM Rental WHERE title LIKE : TITLE AND USER_ID LIKE: USER_ID"
)*/



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RENTAL")
public class Rental {
    private Long id;

    private String title;
    private LocalDate startingDate;
    private LocalDate finishDate;
    private int amountOfBorrowedBooks;
    @Enumerated(EnumType.STRING)
    private Status status;



    public Rental(String title, LocalDate startingDate, LocalDate finishDate, int amountOfBorrowedBooks, Status status) {
        this.title = title;
        this.startingDate = startingDate;
        this.finishDate = finishDate;
        this.amountOfBorrowedBooks = amountOfBorrowedBooks;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    @Column(name = "AMOUNT_OF_BORROWED")
    public int getAmountOfBorrowedBooks() {
        return amountOfBorrowedBooks;
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

    public void setTitle(String title) {
        this.title = title;
    }
}
