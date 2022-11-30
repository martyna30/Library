package com.library.domain;

import com.library.domain.bn.TypeOfObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.InvalidClassException;

import static com.library.domain.bn.TypeOfObject.BOOK;


@NoArgsConstructor
@Entity
@Table(name = "OBJECT_NAME")
public class ObjectName {

    private Long id;

    TypeOfObject typeOfObject;

    Book book;
    Author author;

    private String name;

    public ObjectName(Object object) throws InvalidClassException {
        if (object instanceof Book) {
            this.typeOfObject = TypeOfObject.BOOK;
            this.book = (Book) object;
            this.name = this.book.getTitle();
        } else if (object instanceof Author) {
            this.typeOfObject = TypeOfObject.AUTHOR;
            this.author = (Author) object;
            this.name = this.author.getForename() + " " + this.author.getSurname();
        } else {
            throw new InvalidClassException("Unsupported object type");
        }
    }

    public ObjectName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
    }
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    public Author getAuthor() {
        return author;
    }

    /*@Column(name = "COMMON_OBJECT_ID", unique = true)
    public Long getCommonObjectId() {
        return commonObjectId;
    }*/

    @Column(name = "TYPE_OF_COMMON_OBJECT")
    @Enumerated(EnumType.STRING)
    public TypeOfObject getTypeOfObject() {
        return typeOfObject;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeOfObject(TypeOfObject typeOfObject) {
        this.typeOfObject = typeOfObject;
    }


    public void setBook(Book book) {
        this.book = book;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
