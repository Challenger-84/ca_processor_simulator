package generic;

public class DecrementLRUEvent extends Event {

	public DecrementLRUEvent(long eventTime, Element requestingElement, Element processingElement) {
		super(eventTime, EventType.DecrementLRU, requestingElement, processingElement);

	}
	
}
