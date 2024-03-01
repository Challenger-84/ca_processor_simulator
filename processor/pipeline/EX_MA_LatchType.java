package processor.pipeline;

import generic.ControlSignals;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	
	int pc;
	int instruction;
	
	int ALUresult;
	
	int storeVal;
	
	ControlSignals controlSignals;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		ALUresult = 0;
		storeVal = 0;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
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
	
	public ControlSignals controlSignals() {
		return controlSignals;
	}
	
	public void setControlSignals(ControlSignals signals) {
		controlSignals = signals;
	}
	
	public int ALUEesult() {
		return ALUresult;
	}
	
	public void setALUResult(int value) {
		ALUresult = value;
	}
	
	public int storeVal() {
		return storeVal;
	}
	
	public void setstoreVal(int value) {
		storeVal = value;
	}

}
