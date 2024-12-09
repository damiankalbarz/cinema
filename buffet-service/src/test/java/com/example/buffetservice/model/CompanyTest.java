package com.example.buffetservice.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationForValidCompany() {
        Company company = new Company();
        company.setName("Valid Company Name");
        company.setPhone("+123456789");
        company.setEmail("example@test.com");
        company.setAccountNumber("12345678901234567890123456");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() {
        Company company = new Company();
        company.setName("");
        company.setPhone("+123456789");
        company.setEmail("example@test.com");
        company.setAccountNumber("12345678901234567890123456");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getPropertyPath().toString().equals("name") &&
                        violation.getMessage().equals("Name is required")
        );
    }

    @Test
    void shouldFailValidationWhenPhoneIsInvalid() {
        Company company = new Company();
        company.setName("Valid Name");
        company.setPhone("invalid-phone");
        company.setEmail("example@test.com");
        company.setAccountNumber("12345678901234567890123456");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getPropertyPath().toString().equals("phone") &&
                        violation.getMessage().equals("Phone number must be valid")
        );
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        Company company = new Company();
        company.setName("Valid Name");
        company.setPhone("+123456789");
        company.setEmail("invalid-email");
        company.setAccountNumber("12345678901234567890123456");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getPropertyPath().toString().equals("email") &&
                        violation.getMessage().equals("Email must be valid")
        );
    }

    @Test
    void shouldFailValidationWhenAccountNumberIsInvalid() {
        Company company = new Company();
        company.setName("Valid Name");
        company.setPhone("+123456789");
        company.setEmail("example@test.com");
        company.setAccountNumber("12345");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getPropertyPath().toString().equals("accountNumber") &&
                        violation.getMessage().equals("Account number must be 26 digits")
        );
    }
}