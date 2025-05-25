package org.koreait.member.validators;

import lombok.RequiredArgsConstructor;
import org.koreait.global.validators.MobileValidator;
import org.koreait.global.validators.PasswordValidator;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.repositories.MemberRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Lazy
@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator, MobileValidator {
    private final MemberRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        /**
         * 1. 이메일 중복 여부 체크
         * 2. 비밀번호 복잡성 - 알파벳 대소문자 각각 1개 이상, 숫자 1개 이상, 특수 문자 포함
         * 3. 비밀번호, 비밀번호 확인 일치 여부
         * 4. 휴대폰 번호 검증
         */
        RequestJoin form = (RequestJoin) target;
        String email = form.getEmail();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String mobile = form.getMobile();

        // 1. 이메일 중복 여부 체크
        if (repository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        // 2. 비밀번호 복잡성
        if (!checkAlpha(password, false) || !checkNumber(password) || !checkSpecialChars(password)) {
            errors.rejectValue("password", "Complexity");
        }

        // 3. 비밀번호, 비밀번호 확인 일치 여부
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch");
        }

        if (StringUtils.hasText(mobile) && !checkMobile(mobile)) {
            errors.rejectValue("mobile", "Format");
        }
    }
}
