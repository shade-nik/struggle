package local.halflight.learning.dto.simpletask;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.GenericRequest;

@XmlRootElement(name = "SimpleTaskRequest")
public class SimpleTaskRequest implements GenericRequest<SimpleTask> {

	private SimpleTask payload;

	public SimpleTask getPayload() {
		return payload;
	}

	public void setPayload(SimpleTask payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("SimpleTask", payload).toString();
	}

}
