package com.library.exception;

import com.library.validation.SignatureValidator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class, Format.class, LengthOfCharacters.class, UniqueFormat.class, Default.class })
    public interface OrderChecks {}

