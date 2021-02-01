package com.library.facade;

import com.library.domain.Book;
import com.library.domain.bn.BnBookDto;
import com.library.mapper.BnMapper;
import com.library.mapper.BookMapper;
import com.library.service.BnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BnBookFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BnBookFacade.class);

    @Autowired
    BnService bnService;

    @Autowired
    BnMapper bnMapper;

    @Autowired
    BookMapper bookMapper;

    public List<BnBookDto> getBooksFromBnAndSaveToDatabase() {
        List<Book> bnbooks = bnMapper.mapToBooklist(bnService.getAllBooks());
        Iterable<Book> bnbooksToDatabase = bnService.saveBooksFromBn(bnbooks);
        return bookMapper.mapToBookDtoList(bnbooksToDatabase);
    }


}
