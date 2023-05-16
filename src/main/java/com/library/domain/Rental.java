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

/*@NamedQuery(

        name = "Rental.retrieveRentalWithUserId",
        query = "FROM Rental WHERE user_id LIKE: USER_ID"
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
    //User user;
    Book book;



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
    //@ManyToOne()
    //@JoinColumn(name = "USER_ID")
    //public User getUser() {
       // return user;
    //}

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
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


    public void setBook(Book book) {
        this.book = book;
    }
}
