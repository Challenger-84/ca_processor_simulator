package processor.pipeline;

import configuration.Configuration;
import generic.Simulator;

import processor.Processor;
import generic.Simulator;

// Importing classes related to Events
import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;

import processor.Clock;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if (EX_MA_Latch.isMA_enable()) {
			
			if (EX_MA_Latch.isMABusy()) {
				MA_RW_Latch.setNop(true);
				MA_RW_Latch.setRW_enable(true);
				EX_MA_Latch.setMA_enable(false);
				return;
			}
			
<<<<<<< HEAD
=======
			
>>>>>>> d15df27ca5bebf18a0447b43cd9606de8c53c98e
			MA_RW_Latch.setNop(EX_MA_Latch.isNop());
			if (EX_MA_Latch.isNop()) {
				MA_RW_Latch.setRW_enable(true);
				return;
			}
			
			System.out.println("MA Ins: " + EX_MA_Latch.getInstruction());
			
			// Passing all the other values to Latch
			MA_RW_Latch.setControlSignals(EX_MA_Latch.controlSignals());
			MA_RW_Latch.setALUResult(EX_MA_Latch.ALUResult());
			MA_RW_Latch.setPC(EX_MA_Latch.getPC());
			MA_RW_Latch.setInstruction(EX_MA_Latch.getInstruction());
			MA_RW_Latch.setWriteTox31(EX_MA_Latch.getWriteTox31());
			MA_RW_Latch.setx31(EX_MA_Latch.getx31());
			
			if (EX_MA_Latch.controlSignals().isLd()) {
				
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor .getMainMemory(),
							EX_MA_Latch.ALUResult())
					);
				EX_MA_Latch.setMABusy(true);
				MA_RW_Latch.setRW_enable(true);
				MA_RW_Latch.setNop(true);;
			}
			else if (EX_MA_Latch.controlSignals().isSt()) {
			
				
				Simulator.getEventQueue().addEvent(
					new MemoryWriteEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor .getMainMemory(),
							EX_MA_Latch.ALUResult(),
							EX_MA_Latch.storeVal()
							)
					);
				EX_MA_Latch.setMABusy(true);
				MA_RW_Latch.setRW_enable(true);
				MA_RW_Latch.setNop(true);
			}
			else{
				MA_RW_Latch.setRW_enable(true);
			}
			
			
			EX_MA_Latch.setMA_enable(false);
			
		}
	}
	
	@Override
	public void handleEvent(Event e) {

		if (MA_RW_Latch.isRWBusy()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);			
		}
		else {
			MemoryResponseEvent event =  (MemoryResponseEvent)e;
			
			// If it isn't a MemoryWrite event's Response then set load Result
			if (!event.isWriteFinished()){
				MA_RW_Latch.setLoadResult(event.getValue());
			}
			
			MA_RW_Latch.setNop(false);
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMABusy(false);
		}
	}

}
