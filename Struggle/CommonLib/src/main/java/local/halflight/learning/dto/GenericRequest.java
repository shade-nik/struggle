package local.halflight.learning.dto;

//TODO add GenericResponse to scope of this generic 
public interface GenericRequest<P extends Payload> {
	P getPayload();
}
