package local.halflight.learning.webservice.validation;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.Payload;

public abstract class BaseValidator<RQ extends GenericRequest<? extends Payload> >  implements Validator<RQ> {

}
