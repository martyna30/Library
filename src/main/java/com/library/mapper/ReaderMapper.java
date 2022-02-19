package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {

    public Reader mapToReader(ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getForename(),
                readerDto.getSurname()
        );

    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getForename(),
                reader.getSurname()
        );
    }

    List<Reader> mapToReaderList(final List<ReaderDto> readerDtoList) {
        return readerDtoList.stream()
                .map(readerDto -> new Reader(
                        readerDto.getId(),
                        readerDto.getForename(),
                        readerDto.getSurname()))
                .collect(Collectors.toList());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readerList) {
        return readerList.stream()
                .map(reader -> new ReaderDto(
                        reader.getId(),
                        reader.getForename(),
                        reader.getSurname()))
                .collect(Collectors.toList());
    }
}
