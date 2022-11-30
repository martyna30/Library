package com.library.validationGroup;

//import com.library.validation.SignatureValidator;

import com.library.validationGroup.Format;
import com.library.validationGroup.LengthOfCharacters;
import com.library.validationGroup.NotEmptyGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class, Format.class, LengthOfCharacters.class, UniqueFormat.class,  Default.class })
    public interface OrderChecks {}


