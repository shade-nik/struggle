package local.halflight.learning.dto.simpletask;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.google.common.base.MoreObjects;

public class NonAnnotatedTaskListResponse {

	private SimpleTaskRequest request;

	private List<SimpleTask> payload;

	public NonAnnotatedTaskListResponse(List<SimpleTask> responses) {
		this.payload = responses;
	}

	public SimpleTaskRequest getRequest() {
		return request;
	}

	public void setRequest(SimpleTaskRequest request) {
		this.request = request;
	}

	public List<SimpleTask> getPayload() {
		return payload;
	}

	public void setPayload(List<SimpleTask> payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("SimpleTaskRequest", request).add("SimpleTasksList", payload).toString();
	}
}
