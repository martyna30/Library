package com.library.service;

import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {


    @Autowired
    ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }
    public Optional<Reader> getReader(final Long readerId) {
        return readerRepository.findById(readerId);
    }

    public void deleteReader(final Long readerId) {
        readerRepository.deleteById(readerId);
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }
}
