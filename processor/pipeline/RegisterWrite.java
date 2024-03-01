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
			int instruction = MA_RW_Latch.getInstruction();
			String inst_string = Integer.toBinaryString(instruction);
			
			//gets adress
			int address;
			String rdString = inst_string.substring(5,10);
			address = Integer.parseInt(rdString);
			
			//gets data
			int data;
			if (signals.isLd()) {
				data = MA_RW_Latch.LoadResult();
			} else {
				data = MA_RW_Latch.ALUResult();
			}
			
			if (signals.isWb()) {
				containingProcessor.getRegisterFile().setValue(address, data);
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
