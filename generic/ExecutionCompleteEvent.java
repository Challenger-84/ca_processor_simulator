package generic;

public class ExecutionCompleteEvent extends Event {
	
	int ALUResult;
	
	public ExecutionCompleteEvent(long eventTime, Element requestingElement, Element processingElement, int ALUResult)
	{
		super(eventTime, EventType.ExecutionComplete, requestingElement, processingElement);
		this.ALUResult = ALUResult;
	}

	public int getALUResult() {
		return ALUResult;
	}

	public void setALUResult(int ALUResult) {
		this.ALUResult = ALUResult;
	}
}
