package local.halflight.learning.dto.elasticsearch;

import java.util.Calendar;
import java.util.Date;

import org.elasticsearch.search.aggregations.support.format.ValueFormat.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.google.common.base.MoreObjects;

@Document(indexName="info", type="log_message")
public class LogMessage {
	
	@Id
	private String id;
	
	@Field(
			type = FieldType.Date,
			index = FieldIndex.not_analyzed,
			store = true
// this for DateFormat.custom but needs some fixing		pattern = "dd.MM.yyyy hh:mm:ss:SS"
	)
	private Calendar date;

	private LogLevel logLevel;  
	private String className;
	
	private String threadName;

	@Field(
			type = FieldType.String,
			index = FieldIndex.analyzed,
			store = true
	)
	private String logMessage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public LogLevel getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getLogMessage() {
		return logMessage;
	}
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String toString() {
//@formatter:off
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("date", date)
				.add("loglevel", logLevel)
				.add("className", className )
				.add("threadName", threadName )
				.add("logMessage", logMessage )
				.toString();
//@formatter:on
	}
}
