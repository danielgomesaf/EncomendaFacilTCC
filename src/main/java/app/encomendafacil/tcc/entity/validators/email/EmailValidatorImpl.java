package app.encomendafacil.tcc.entity.validators.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {

	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	public void initialize(EmailValidator annotation) {
		pattern = Pattern.compile(EMAIL_PATTERN);
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
