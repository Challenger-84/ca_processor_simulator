package processor.pipeline;

import generic.Simulator;
import processor.Processor;
import generic.ControlSignals;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			ControlSignals signals = MA_RW_Latch.controlSignals();
			
			int address;
			if (signals.isCall()){
				// TODO: Implement getting address from either rd or ra
			}else {
				address = 0;
			}
			
			int data;
			if (signals.isLd() && signals.isCall()) {
				data = MA_RW_Latch.ALUResult();
			} else if (signals.isLd() && !signals.isCall()) {
				data = MA_RW_Latch.LoadResult();
			} else {
				// TODO: Do PC + 4
				data = 4;
			}
			
			if (signals.isWb()) {
				containingProcessor.getRegisterFile().setValue(address, data);
			}
			
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
