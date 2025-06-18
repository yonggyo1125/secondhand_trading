package org.koreait.survey.diabetes.validators;

import org.koreait.survey.diabetes.controllers.RequestDiabetesSurvey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DiabetesSurveyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestDiabetesSurvey.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestDiabetesSurvey form = (RequestDiabetesSurvey) target;
        String mode = form.getMode();

        if (StringUtils.hasText(mode) && mode.equals("step2")) { // step2 검증
            validateStep2(form, errors);
        } else { // step1 검증
            validateStep1(form, errors);
        }
    }

    // step1 검증
    private void validateStep1(RequestDiabetesSurvey form, Errors errors) {

    }

    // step2 검증
    private void validateStep2(RequestDiabetesSurvey form, Errors errors) {

    }
}
