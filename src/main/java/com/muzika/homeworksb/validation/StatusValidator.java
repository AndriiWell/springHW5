package com.muzika.homeworksb.validation;

import com.muzika.homeworksb.enums.StatusEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<Status, StatusEnum> {
    @Override
    public boolean isValid(StatusEnum status, ConstraintValidatorContext constraintValidatorContext) {
        // Check for status values executes before enter to this method, so to check its value here no sence.
        // Maybe some complex logic need to do, but values of StatusEnum Spring can check by it own.
        if (null == status) { // This operation has no sense, the Spring will do it before entrance to the method.
            return false;
        }
        boolean isValid = StatusEnum.COMPLETED == status
            || StatusEnum.IN_PROGRESS == status
            || StatusEnum.PENDING == status
            || StatusEnum.TODO == status
            || StatusEnum.IN_QA_HANDS == status
            || StatusEnum.REVIEW == status
            || StatusEnum.READY_FOR_QA == status;

        return isValid;
    }
}
