package local.halflight.learning.dto;

import com.google.common.base.MoreObjects;

public class BaseRequest<P extends Payload> implements GenericRequest<P> {

	protected P payload;
	
	@Override
	public P getPayload() {
		return payload;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Payload", payload)
				.toString();
	}
}
