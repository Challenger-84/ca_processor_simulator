package processor.pipeline;

import processor.Processor;
import processor.Clock;
import generic.Simulator;
import configuration.Configuration;

// imports regarding events
import generic.CacheReadEvent;
import generic.CacheResponseEvent;
import generic.Element;
import generic.Event;


public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	boolean branchTaken;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		
		branchTaken = false;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{	
			
			if (IF_EnableLatch.isIFBusy()) {
				IF_OF_Latch.setNop(true);
				IF_OF_Latch.setOF_enable(true);
				return;
			}
			
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			
			Simulator.getEventQueue().addEvent(new CacheReadEvent(
					Clock.getCurrentTime() + Configuration.L1i_latency,
					this,
					containingProcessor.getL1i_Cache(),
					currentPC
					));
			
			IF_OF_Latch.setPC(currentPC);
			
			IF_EnableLatch.setIFBusy(true);
			IF_OF_Latch.setOF_enable(true);
			IF_OF_Latch.setNop(true);
			
			
		}
	}
	
	public void performBranch() 
	{
		if(EX_IF_Latch.isIF_enable()) 
		{	
			if (EX_IF_Latch.isBranchTaken()) {
				
				EX_IF_Latch.setBranchTaken(false);
				branchTaken = true;
				
				
				int newPC = EX_IF_Latch.branchTarget();
				containingProcessor.getRegisterFile().setProgramCounter(newPC);
				
				IF_OF_Latch.setInstruction(0);
				IF_OF_Latch.setPC(newPC);
				
				
				EX_IF_Latch.setIF_enable(false);
			}
			
		}
		
	}
	
	@Override
	public void handleEvent(Event e) {
		if (IF_OF_Latch.isOFBusy() || !IF_EnableLatch.isIF_enable()) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
			IF_EnableLatch.setIF_enable(true);
		} 
		else 
		{
			if (e.getEventType() == Event.EventType.CacheResponse) {
				if (branchTaken) {
					IF_OF_Latch.setOF_enable(true);
					IF_OF_Latch.setNop(true);
					IF_EnableLatch.setIFBusy(false);
					branchTaken = false;
					return;
				}
				CacheResponseEvent event = (CacheResponseEvent) e;
				
				System.out.println("instruction: " + event.getValue());
				
				containingProcessor.getRegisterFile().setProgramCounter(IF_OF_Latch.getPC() + 1);
				
				IF_OF_Latch.setInstruction(event.getValue());
				IF_OF_Latch.setNop(false);
				IF_OF_Latch.setOF_enable(true);
				IF_EnableLatch.setIFBusy(false);
			}
		}	
	}
	

}
