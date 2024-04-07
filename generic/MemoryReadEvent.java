package generic;

public class MemoryReadEvent extends Event {

	int addressToReadFrom;
	Element requestingUnit;
	
	public MemoryReadEvent(long eventTime, Element requestingElement, Element processingElement, int address, Element requestingUnit) {
		super(eventTime, EventType.MemoryRead, requestingElement, processingElement);
		this.addressToReadFrom = address;
		this.requestingUnit = requestingUnit;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}
	
	public Element getRequestingUnit() {
		return requestingUnit;
	}
}
