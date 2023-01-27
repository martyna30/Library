package com.library.domain.rental;

import com.library.domain.Book;
import com.library.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalBook {

    Book book;

    User user;

}
