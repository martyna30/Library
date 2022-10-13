package com.library.exception;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value={NotEmptyGroup.class, Format.class, LengthOfCharacters.class, Default.class })
public interface OrderChecks2 {
}
