package local.halflight.learning.dto.user;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.MoreExecutors;

@XmlRootElement(name = "StruggleUsersResponse" )
@XmlAccessorType(XmlAccessType.NONE)
public class StruggleUsersResponse {

	@XmlElementWrapper(name = "StruggleUsersList")
	@XmlElement(name = "StruggleUser")
	private List<StruggleUser> users;

	public StruggleUsersResponse() {
	}
	
	public StruggleUsersResponse(List<StruggleUser> users) {
		this.users = users;
	}

	public List<StruggleUser> getUsers() {
		return users;
	}

	public void setUsers(List<StruggleUser> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("users", users).toString();
	}
}
