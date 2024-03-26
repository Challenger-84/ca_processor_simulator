package generic;

public class MemoryResponseEvent extends Event {

	int value;
	boolean writeFinished;
	
	public MemoryResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value, boolean writeFinished) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
		this.writeFinished = writeFinished;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isWriteFinished() {
		return writeFinished;
	}
	
	public void setWriteFinished(boolean writeFinished) {
		this.writeFinished = writeFinished;
	}
	
}
