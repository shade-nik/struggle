package local.halflight.learning.validation;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.Payload;

public abstract class BaseWebRequestValidator<RQ extends GenericRequest<? extends Payload> >  implements Validator<RQ> {

}
