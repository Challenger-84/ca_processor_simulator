package generic;

public class CacheResponceEvent extends Event{

	int value;
	int address;
	boolean writeFinished;
	
	public CacheReadEvent(long eventTime, Element requestingElement, Element processingElement, int value, int addr ,boolean writeFinished) {
		super(eventTime, EventType.CacheResponse, requestingElement, processingElement);
		this.value = value;
		this.writeFinished = writeFinished;
		this.address = addr;
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
	
	public int getAddr() {
		return address;
	}
    
}
