package com.library.facade;

import com.library.domain.Book;
import com.library.domain.bn.ExternalRequestBookDto;
import com.library.domain.bn.ExternalResponseBookDto;
import com.library.mapper.BnMapper;
import com.library.mapper.BookMapper;
import com.library.service.BnService;
import com.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class BnBookFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BnBookFacade.class);

    @Autowired
    BnService bnService;

    @Autowired
     BookService bookService;

    @Autowired
    BnMapper bnMapper;

    @Autowired
    BookMapper bookMapper;

    public List<ExternalResponseBookDto> getBooksFromBnAndSaveToDatabase(ExternalRequestBookDto externalRequestBookDto) {
        List<Book> bnbooks = bnMapper.mapToBooklist(bnService.getBooks(externalRequestBookDto));  //pobrane z Bn i zmapowane na dto, nastepnie zamienione na na encje
        Book bnBook = bookService.saveBook(bnbooks.get(0));  //zapis pojedynczo do bazy
        return bnMapper.mapToBnBookDtolist(asList(bnBook));     //zamiana na liste, znowu na dto i do kontrolera
    }

    /*public ExternalResponseBookDto getBookFromBnAndSaveToDatabase(ExternalRequestBookDto externalRequestBookDto) {
        Book bnBook = bnMapper.mapToBook(bnService.getBook(externalRequestBookDto));
        Book bnbookToSave = bnService.saveBookFromBn(bnBook);
        return bnMapper.mapToBookDto(bnbookToSave);
    }*/

}
