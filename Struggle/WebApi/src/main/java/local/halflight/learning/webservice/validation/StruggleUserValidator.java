package local.halflight.learning.webservice.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import local.halflight.learning.dto.struggleuser.StruggleUser;

@Component
public class StruggleUserValidator {

	public void validateCreate(StruggleUser payload) {
		if (payload != null) {
			if (payload.getUserUUID() == null) {
				payload.setUserUUID(UUID.randomUUID().toString());
			}
		}
	}

	public void validateUpdate(StruggleUser payload) {
		if (payload != null) {
			if (payload.getUserUUID() == null) {
				payload.setUserUUID(UUID.randomUUID().toString());
			}
		}
	}

}
