package com.library.domain;

import com.library.domain.bn.TypeOfObject;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.InvalidClassException;


@NoArgsConstructor
@Entity
@Table(name = "OBJECT_NAME")
public class ObjectName {

    private Long id;

    TypeOfObject typeOfObject;

    private String name;

    Book book;
    Author author;

    public ObjectName(String name, Object object) throws InvalidClassException {
        this.name = name;
        if (object instanceof Book) {
            this.typeOfObject = TypeOfObject.BOOK;
            book = (Book) object;
            this.name = book.getTitle();
        } else if (object instanceof Author) {
            this.typeOfObject = TypeOfObject.AUTHOR;
            author = (Author) object;
            this.name = author.getForename() + " " + author.getSurname();
        } else {
            throw new InvalidClassException("Unsupported object type");
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TYPE_OF_COMMON_OBJECT")
    @Enumerated(EnumType.STRING)
    public TypeOfObject getTypeOfObject(){
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

}
