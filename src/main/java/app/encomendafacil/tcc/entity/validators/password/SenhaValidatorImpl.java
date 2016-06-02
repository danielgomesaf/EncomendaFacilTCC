package app.encomendafacil.tcc.entity.validators.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SenhaValidatorImpl implements ConstraintValidator<SenhaValidator, String> {

	private Pattern pattern;
	private Matcher matcher;
	
	private static final String SENHA_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,10})";
	
	@Override
	public void initialize(SenhaValidator constraintAnnotation) {
		pattern = Pattern.compile(SENHA_PATTERN);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		matcher = pattern.matcher(value);
		return matcher.matches();
	}

	
	
}
