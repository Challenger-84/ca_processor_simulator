package processor.pipeline;

import processor.Processor;
import generic.Simulator;

public class MemoryAccess {
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
			
			
			System.out.println("MA Ins: " + EX_MA_Latch.getInstruction());
			
			if (EX_MA_Latch.controlSignals().isLd()) {
				
				int ldResult = containingProcessor.getMainMemory().getWord(EX_MA_Latch.ALUResult());
				MA_RW_Latch.setLoadResult(ldResult);
			}
			
			if (EX_MA_Latch.controlSignals().isSt()) {
			
				containingProcessor.getMainMemory().setWord(EX_MA_Latch.ALUResult(), EX_MA_Latch.storeVal());
			}
			
			// Passing all the other values to Latch
			MA_RW_Latch.setControlSignals(EX_MA_Latch.controlSignals());
			MA_RW_Latch.setALUResult(EX_MA_Latch.ALUResult());
			MA_RW_Latch.setPC(EX_MA_Latch.getPC());
			MA_RW_Latch.setInstruction(EX_MA_Latch.getInstruction());
			
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_enable(false);
			
		}
	}

}
