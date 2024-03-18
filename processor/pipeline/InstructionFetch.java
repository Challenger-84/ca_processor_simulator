package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
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
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			IF_OF_Latch.setInstruction(newInstruction);
			IF_OF_Latch.setPC(currentPC);
			
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
			numOfIns++;
			
			//IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
			
			System.out.println("instruction: " + newInstruction);
			
		}
	}
	
	public void performBranch() 
	{
		if(EX_IF_Latch.isIF_enable()) 
		{	
			if (EX_IF_Latch.isBranchTaken()) {
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
	
	public int getNumofInstructions() {
		return numOfIns;
	}

}
