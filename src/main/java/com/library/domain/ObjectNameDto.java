package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectNameDto {

    private Long id;

    //private Long object_id;
    //@NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    private String name;


}

