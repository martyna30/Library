package com.library.bn.converter;

import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class StringToArrayConverter extends StdConverter<String, ArrayList> {

    @Override
    public ArrayList convert(String value) {
        return new ArrayList(Arrays.asList(value));
    }
}
