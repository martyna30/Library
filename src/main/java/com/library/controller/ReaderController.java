package com.library.controller;

import com.library.domain.BookTagDto;
import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class ReaderController {
    @Autowired
    ReaderService readerService;

    @Autowired
    ReaderMapper readerMapper;


    @GetMapping("getReaders")
    public List<ReaderDto> getAllReaders() {
        return readerMapper.mapToReaderDtoList(readerService.getAllReaders());
    }

    @GetMapping("getReader")
    public ReaderDto getReader(@RequestParam Long readerId) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(readerService.getReader(readerId).orElseThrow(ReaderNotFoundException::new));
    }

    @DeleteMapping( "deleteReader")
    public void deleteReader(@RequestParam Long readerId) {
        readerService.deleteReader(readerId);
    }

    @PutMapping("updateReader")
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        return readerMapper.mapToReaderDto(readerService.saveReader(readerMapper.mapToReader(readerDto)));
    }

    @PostMapping( "createReader")
    public void createReader(@Valid @RequestBody ReaderDto readerDto) {
        readerService.saveReader(readerMapper.mapToReader(readerDto));
    }
}
