package app.encomendafacil.tcc.entity.validators.email;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=EmailValidatorImpl.class)
public @interface EmailValidator {

	String message() default "E-mail inválido";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
