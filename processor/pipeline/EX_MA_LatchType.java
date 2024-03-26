package processor.pipeline;

import generic.ControlSignals;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	boolean MA_busy;
	
	int pc;
	int instruction;
	
	int ALUresult;
	
	int storeVal;
	
	boolean writeTox31;
	int x31_value;
	
	ControlSignals controlSignals;
	
	boolean isNop;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		ALUresult = 0;
		storeVal = 0;
		
		writeTox31 = false;
		x31_value = 0;
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
	
	public int ALUResult() {
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
	
	public void setWriteTox31(boolean value) {
		writeTox31 = value;
	}
	
	public boolean getWriteTox31() {
		return writeTox31;
	}
	
	public void setx31(int value) {
		x31_value = value;
	}
	
	public int getx31() {
		return x31_value;
	}
	
    public boolean isMABusy() {
        return MA_busy;
    }

    public void setMABusy(boolean isMA_busy) {
        this.MA_busy = isMA_busy;
    }

    public boolean isNop() {
    	return isNop;
    }
    
    public void setNop(boolean isnop) {
    	isNop = isnop;
    }
}
