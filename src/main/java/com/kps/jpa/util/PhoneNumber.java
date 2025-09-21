package com.kps.jpa.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.METHOD,ElementType.FIELD})
@Constraint(validatedBy = PhoneValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

	String message() default "Invalid Phone number";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};
}
