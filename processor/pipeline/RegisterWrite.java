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
			
			System.out.println("RW Ins: " + MA_RW_Latch.instruction);
			
			// End simulation if instruction is end
			if (MA_RW_Latch.controlSignals().isEnd()) {
				System.out.println("ending!");
				Simulator.setSimulationComplete(true);
				return;
			}
			
			ControlSignals signals = MA_RW_Latch.controlSignals();
			int instruction = MA_RW_Latch.getInstruction();
			String inst_string = Integer.toBinaryString(instruction);
			if (inst_string.length() != 32) {
				inst_string = "0".repeat(32-inst_string.length()) + inst_string;
			}
			
			//gets adress
			int address;
			String rdString;
			if (signals.isImmediate()) {
				rdString = inst_string.substring(10,15);
			} else {
				rdString = inst_string.substring(15,20);
			}
			address = Integer.parseInt(rdString, 2);
			
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
			

			//unlock rd
			if(containingProcessor.getRegisterLock(address) != 0){
				containingProcessor.unlockRegister(address);
			}
			
			// Write to x31 if needed to be written to
			if (MA_RW_Latch.getWriteTox31()) {
				containingProcessor.getRegisterFile().setValue(31, MA_RW_Latch.getx31());
				containingProcessor.unlockRegister(31);					//unlock x31
			}
			
			MA_RW_Latch.setRW_enable(false);
			//IF_EnableLatch.setIF_enable(true);
		}
	}

}
