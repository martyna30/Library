package com.library.bn.converter;

import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntConverter extends StdConverter <String, Integer> {


    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
