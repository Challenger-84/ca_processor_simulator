package generic;


public class MemoryResponseEvent extends Event {

	int value;
	int address;
	boolean writeFinished;
	Element requestingUnit;
	
	public MemoryResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value, int addr ,boolean writeFinished, Element requestingUnit) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
		this.writeFinished = writeFinished;
		this.address = addr;
		this.requestingUnit = requestingUnit;
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
	
	public Element getRequestingUnit() {
		return requestingUnit;
	}
	
}
