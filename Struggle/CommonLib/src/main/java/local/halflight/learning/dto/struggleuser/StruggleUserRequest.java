package local.halflight.learning.dto.struggleuser;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.BaseRequest;

@XmlRootElement(name = "StruggleUserRequest")
public class StruggleUserRequest extends BaseRequest<StruggleUser> {

	private StruggleUser payload;

	public StruggleUser getPayload() {
		return payload;
	}

	public void setPayload(StruggleUser payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("StruggleUser", payload).toString();
	}

}
