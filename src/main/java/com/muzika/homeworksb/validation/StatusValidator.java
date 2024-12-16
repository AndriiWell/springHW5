package com.muzika.homeworksb.validation;

import com.muzika.homeworksb.enums.StatusEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<Status, StatusEnum> {
    @Override
    public boolean isValid(StatusEnum status, ConstraintValidatorContext constraintValidatorContext) {
        if (null == status) {
            return false;
        }
        boolean isValid = StatusEnum.COMPLETED == status
            || StatusEnum.IN_PROGRESS == status
            || StatusEnum.PENDING == status
            || StatusEnum.TODO == status
            || StatusEnum.IN_QA_HANDS == status
            || StatusEnum.REVIEW == status
            || StatusEnum.READY_FOR_QA == status;

        if (!isValid) {
            throw new RuntimeException("Status has is not correct value!");
        }

        return isValid;
    }
}
