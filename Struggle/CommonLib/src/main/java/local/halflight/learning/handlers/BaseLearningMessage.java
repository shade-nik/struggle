package local.halflight.learning.handlers;

public class BaseLearningMessage implements LearningMessage {

	String statusString;
	
	
	public String getStatusString() {
		return statusString;
	}


	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}


	@Override
	public LearningPayload getPayload() {
		// TODO Auto-generated method stub
		return null;
	}

}
