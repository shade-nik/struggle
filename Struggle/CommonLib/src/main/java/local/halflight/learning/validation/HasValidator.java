package local.halflight.learning.validation;

@FunctionalInterface
public interface HasValidator {
	Validator getValidator();
}
