package processor.pipeline;

import generic.ControlSignals;

public class MA_RW_LatchType {
	
	boolean RW_enable = false;
		
	int pc;
	int instruction;
	
	int ldResult;
	int ALUresult;
	
	boolean writeTox31;
	int x31_value;
	
	ControlSignals controlSignals;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		ldResult = 0;

		writeTox31 = false;
		x31_value = 0;
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
	
    public boolean isRWBusy() {
        return RW_busy;
    }

    public void setRWBusy(boolean isRW_busy) {
        this.RW_busy = isRW_busy;
    }

}
