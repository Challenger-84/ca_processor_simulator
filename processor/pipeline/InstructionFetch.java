package processor.pipeline;

import processor.Processor;
import processor.Clock;
import generic.Statistics;
import generic.Simulator;
import configuration.Configuration;

// imports regarding events
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;


public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	int numOfIns;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		
		this.numOfIns = 0;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{	
			
			if (IF_EnableLatch.isIFBusy()) {
				return;
			}
			
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(
					Clock.getCurrentTime() + Configuration.mainMemoryLatency,
					this,
					containingProcessor.getMainMemory(),
					currentPC
					));
			
			IF_OF_Latch.setPC(currentPC);
			
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
			numOfIns++;
			
			IF_EnableLatch.setIFBusy(true);
			
			//IF_EnableLatch.setIF_enable(false);
			//IF_OF_Latch.setOF_enable(true);
			
		}
	}
	
	public void performBranch() 
	{
		if(EX_IF_Latch.isIF_enable()) 
		{	
			if (EX_IF_Latch.isBranchTaken()) {
				
				Statistics stats = new Statistics();
				stats.incrementNumberOfNops(1);
				
				int newPC = EX_IF_Latch.branchTarget();
				containingProcessor.getRegisterFile().setProgramCounter(newPC);
				
				IF_OF_Latch.setInstruction(0);
				IF_OF_Latch.setPC(newPC);
				
				// If we take branch that means 2 wrong instructions came in
				numOfIns -= 2;
				
				EX_IF_Latch.setIF_enable(false);
			}
			
		}
		
	}
	
	@Override
	public void handleEvent(Event e) {
		if (IF_OF_Latch.isOFBusy()) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		} 
		else 
		{
			if (e.getEventType() == Event.EventType.MemoryResponse) {
				MemoryResponseEvent event = (MemoryResponseEvent) e;
				
				System.out.println("instruction: " + event.getValue());
				
				IF_OF_Latch.setInstruction(event.getValue());
				IF_OF_Latch.setOF_enable(true);
				IF_EnableLatch.setIFBusy(false);
			}
		}	
	}
	
	public int getNumofInstructions() {
		return numOfIns;
	}

}
