package local.halflight.learning.dto.elasticsearch;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.common.base.MoreObjects;

import local.halflight.learning.dto.GenericRequest;
import local.halflight.learning.dto.Payload;

@Document(indexName="info", type = "validation_message")
public class ValidationInfoMessage {

	@Id
	private String id;
	
	private String dtoType;
	
	@Field(type= FieldType.Nested)
	private GenericRequest originalRequest;

	private DateTime date;

	@Field(type=FieldType.Nested)
	private List<ValidationMessage> messages;



	@Override
	public String toString() {
		//@formatter:off
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("dtoType", dtoType)
				.add("date", date)
				.add("originalRequest", originalRequest)
				.add("messages", messages)
				.toString();
		//@formatter:on
	}


}
