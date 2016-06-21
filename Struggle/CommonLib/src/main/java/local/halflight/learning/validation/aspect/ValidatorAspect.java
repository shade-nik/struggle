package local.halflight.learning.validation.aspect;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.validationerror.ValidationError;
import local.halflight.learning.dto.validationerror.ValidationErrorLevel;
import local.halflight.learning.validation.HasValidator;
import local.halflight.learning.validation.Validator;

@Component
@Aspect
public class ValidatorAspect {

	private static final Logger LOG = LoggerFactory.getLogger(ValidatorAspect.class);

	@Autowired
	private ValidationErrorHandlingStrategy errorHandlingStrategy;

	/***
	 * AOP advice around methods annotated with {@link UseValidator} Method
	 * first argument should be {@link GenericRequest} or annotated with
	 * {@link ValidationTarget}
	 * 
	 * @throws Throwable
	 * 
	 */

	@Around("@annotation(useValidator)")
	public Object validateAndHandle(ProceedingJoinPoint joinPoint, final UseValidator useValidator) throws Throwable {
		Object result = null;
		GenericRequest target = getValidationTarget(joinPoint);
		Validator validator = getValidator(joinPoint);

		Object jpTarget = joinPoint.getTarget();
		LOG.debug("Joint point target: {}", jpTarget);

		try {
			Map<ValidationErrorLevel, ValidationError> errors = validator.validate(target);
			
			result =  joinPoint.proceed();

		} catch (Throwable e) {
			LOG.error("Processing error: ", e);
			throw e;
		}

		return result;
	}

	private Validator getValidator(ProceedingJoinPoint joinPoint) {
		Validator validator = null;
		Object targetThis = joinPoint.getThis();
		LOG.debug("Link to joint point owner: {}", targetThis);
		
		if (targetThis instanceof HasValidator) {
			validator = ((HasValidator) targetThis).getValidator();
		}
		if(validator == null) {
			throw new RuntimeException("Validator for current joint point not found.");
		}
		return validator;
	}

	private GenericRequest getValidationTarget(ProceedingJoinPoint joinPoint) {
		GenericRequest rq = null;
		Object[] args = joinPoint.getArgs();
		LOG.debug("Joint point args: {}", args);
		Signature signature = joinPoint.getSignature();
		LOG.debug("Joint point signature: {}", signature);

		
		
		if (args != null && args.length > 0) {
			for (Object arg : args) {
				if (arg instanceof GenericRequest) {
					if(arg.getClass().getAnnotation(ValidationTarget.class) != null)
					{	
						rq = (GenericRequest) arg;
					}
				}
			}
		}
		

		if (rq == null) {
			throw new RuntimeException("Can't find validation target");
		}
		
		return rq;
	}

}
