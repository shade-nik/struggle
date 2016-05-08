package local.halflight.learning.dto.simpletask;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

@XmlRootElement(name = "SimpleTaskResponse")
public class SimpleTaskResponse {

	private SimpleTaskRequest request;

	@XmlElement(name = "SimpleTaskPayload")
	private SimpleTask payload;
	
	public SimpleTaskResponse() {
	}
	
	public SimpleTaskResponse(SimpleTask payload) {
		this.payload = payload;
	}

	public SimpleTaskResponse(SimpleTaskRequest request) {
		this.request = request;
	}
	public SimpleTaskRequest getRequest() {
		return request;
	}

	public void setRequest(SimpleTaskRequest request) {
		this.request = request;
	}
	
	public SimpleTask getPayload() {
		return payload;
	}

	public void setPayload(SimpleTask payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("SimpleTaskRequest", request)
				.add("SimpleTask", payload)
				.toString();
	}
}
