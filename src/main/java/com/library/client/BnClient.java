package com.library.client;

import com.library.bn.config.BnConfig;
import com.library.domain.bn.BnBookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;


@Component
public class BnClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BnClient.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BnConfig bnConfig;

    public BnBookDto getBookFromBn() {
        URI url = UriComponentsBuilder.fromHttpUrl(bnConfig.getBnApiEndpoint())
                .build().encode().toUri();


        try {
            BnBookDto bnBookDtoResponse = restTemplate.getForObject(url, BnBookDto.class);
            System.out.println(bnBookDtoResponse);
            return  bnBookDtoResponse;

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(),e);
            return this.getBookFromBn();
        }

    }

    public List<BnBookDto> getBooksFromBn() {
        URI url = UriComponentsBuilder.fromHttpUrl(bnConfig.getBnApiEndpoint())
                .build().encode().toUri();

        try {
            BnBookDto[] bnBookDtoResponse = restTemplate.getForObject(url, BnBookDto[].class);
            System.out.println(bnBookDtoResponse);
            return Arrays.asList(ofNullable(bnBookDtoResponse).orElse(new BnBookDto[0]));

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(),e);
            return new ArrayList<>();
        }

    }

}
