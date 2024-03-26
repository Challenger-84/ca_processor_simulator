package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean OF_busy;
	
	int pc;
	int instruction;
	
	boolean isNop;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
		OF_busy = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}
	
	public void setOFBusy(boolean OF_busy) {
		this.OF_busy = OF_busy;
	}
	
	public boolean isOFBusy() {
		return OF_busy;
	}


	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	
	public int getPC() {
        return pc;
    }
	
	public void setPC(int pc) {
        this.pc = pc;
    }
	
    public boolean isNop() {
    	return isNop;
    }
    
    public void setNop(boolean isnop) {
    	isNop = isnop;
    }

}
