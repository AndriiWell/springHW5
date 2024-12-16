package com.muzika.homeworksb.validation;

import com.muzika.homeworksb.enums.PriorityEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority, PriorityEnum> {

    @Override
    public boolean isValid(PriorityEnum priority, ConstraintValidatorContext constraintValidatorContext) {
        return null == priority
            || PriorityEnum.LOW == priority
            || PriorityEnum.CRITICAL == priority
            || PriorityEnum.HIGH == priority
            || PriorityEnum.MEDIUM == priority
            || PriorityEnum.MAYBE_SOME_DAY == priority;
    }
}
