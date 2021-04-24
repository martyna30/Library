package com.library.controller;

import com.library.domain.bn.ExternalRequestBookDto;
import com.library.domain.bn.ExternalResponseBookDto;
import com.library.facade.BnBookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/bn")
public class BnBookController {

    @Autowired
    BnBookFacade bnBookFacade;


    @RequestMapping(method = RequestMethod.GET, value = "getBooksFromBn")
    public List<ExternalResponseBookDto> getBooksFromBn(@RequestBody ExternalRequestBookDto externalRequestBookDto) {
        return bnBookFacade.getBooksFromBnAndSaveToDatabase(externalRequestBookDto);
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "getBookFromBn")
    public ExternalResponseBookDto getBookFromBn() {
        return bnBookFacade.getBookFromBnAndSaveToDatabase();
    }*/
}
