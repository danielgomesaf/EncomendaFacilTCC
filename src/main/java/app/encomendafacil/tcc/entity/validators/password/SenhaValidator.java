package app.encomendafacil.tcc.entity.validators.password;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=SenhaValidatorImpl.class)
public @interface SenhaValidator {

	String message() default "Senha inválida";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
