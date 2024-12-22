package com.muzika.homeworksb.validation;

import com.muzika.homeworksb.enums.PriorityEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority, PriorityEnum> {

    @Override
    public boolean isValid(PriorityEnum priority, ConstraintValidatorContext constraintValidatorContext) {
        // Before this method Spring will check input value of priority according to possible values in PriorityEnum.
        // And if Value exists in PriorityEnum in such case code will enter in this method.
//        return null == priority
//            || PriorityEnum.LOW == priority
//            || PriorityEnum.CRITICAL == priority
//            || PriorityEnum.HIGH == priority
//            || PriorityEnum.MEDIUM == priority
//            || PriorityEnum.MAYBE_SOME_DAY == priority;
        return true;
    }
}
