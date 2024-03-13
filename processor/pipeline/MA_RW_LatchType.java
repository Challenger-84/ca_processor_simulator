package processor.pipeline;

import generic.ControlSignals;

public class MA_RW_LatchType {
	
	boolean RW_enable = false;
		
	int pc;
	int instruction;
	
	int ldResult;
	int ALUresult;
	
	ControlSignals controlSignals;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		ldResult = 0;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public int getPC() {
        return pc;
    }
	
	public void setPC(int pc) {
        this.pc = pc;
    }
	
	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	
	public int LoadResult() {
		return ldResult;
	}
	
	public void setLoadResult(int value) {
		ldResult = value;
	}
	
	public int ALUResult() {
		return ALUresult;
	}
	
	public void setALUResult(int value) {
		ALUresult = value;
	}
	
	public ControlSignals controlSignals() {
		return controlSignals;
	}
	
	public void setControlSignals(ControlSignals signals) {
		controlSignals = signals;
	}
	
}
