package com.library.client;

import com.library.bn.config.BnConfig;
import com.library.domain.bn.ExternalResponseBookDto;
import com.library.domain.bn.ExternalRequestBookDto;
import com.library.domain.bn.ExternalResponseBookListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;


///@Component
public class BnClient {

    /*private static final Logger LOGGER = LoggerFactory.getLogger(BnClient.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BnConfig bnConfig;

    /*public ExternalResponseBookDto getBookFromBn(ExternalRequestBookDto externalRequestBookDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(bnConfig.getBnApiEndpoint()).queryParam("title", externalRequestBookDto.getTitle())
                .build().encode().toUri();

        try {
            ExternalResponseBookDto externalResponseBookDtoResponse = restTemplate.getForObject(url, ExternalResponseBookDto.class);
            System.out.println(externalResponseBookDtoResponse);
            return externalResponseBookDtoResponse;

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(),e);
            return new ExternalResponseBookDto();
        }



    public List<ExternalResponseBookDto> getBooksFromBn(ExternalRequestBookDto externalRequestBookDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(bnConfig.getBnApiEndpoint())
                .queryParam("title", externalRequestBookDto.getTitle())
                .build().encode().toUri();

        try {
            ExternalResponseBookListDto[] externalResponseBookListDto = restTemplate.getForObject(url, ExternalResponseBookListDto[].class);
            System.out.println(externalResponseBookListDto);
            return new ArrayList<>();
            //return Arrays.asList(ofNullable(ExternalResponseBookDto).orElse(new ExternalResponseBookDto[0]));

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }*/
}
