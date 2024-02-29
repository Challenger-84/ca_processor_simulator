package generic;

import java.util.HashMap;

public class ControlSignals {

	boolean isSt;
	boolean isLd;
	boolean isWb;
	boolean isCall;
	boolean isRet;
	
	boolean isImmediate;
	
	boolean isBeq;
	boolean isBgt;
	boolean isBne;
	boolean isBlt;
	
	boolean isUBranch;
	
	boolean isEnd;
	
	HashMap<String, Boolean> aluSignals = new HashMap<>();

	public ControlSignals() {
		this.isSt = false;
        this.isLd = false;
        this.isWb = false;
        this.isCall = false;
        this.isRet = false;

        this.isImmediate = false;

        this.isBeq = false;
        this.isBgt = false;
        this.isUBranch = false;
        
        this.isEnd = false;
        
    	aluSignals.put("add", false);
    	aluSignals.put("sub", false);
    	aluSignals.put("mul", false);
    	aluSignals.put("div", false);
    	aluSignals.put("and", false);
    	aluSignals.put("or", false);
    	aluSignals.put("xor", false);
    	aluSignals.put("slt", false);
    	aluSignals.put("sll", false);
    	aluSignals.put("srl", false);
    	aluSignals.put("sra", false);
	}
	
	// Getters
	public boolean isSt() {
	    return isSt;
	}
	
	public boolean isLd() {
	    return isLd;
	}
	
	public boolean isWb() {
	    return isWb;
	}
	
	public boolean isCall() {
	    return isCall;
	}
	
	public boolean isRet() {
	    return isRet;
	}
	
	public boolean isImmediate() {
	    return isImmediate;
	}
	
	public boolean isBeq() {
	    return isBeq;
	}
	
	public boolean isBgt() {
	    return isBgt;
	}
	
	public boolean isUBranch() {
	    return isUBranch;
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	
	public boolean isBne() {
        return isBne;
    }

    public boolean isBlt() {
        return isBlt;
    }
	
	// Setters
	public void setSt(boolean isSt) {
	    this.isSt = isSt;
	}
	
	public void setLd(boolean isLd) {
	    this.isLd = isLd;
	}
	
	public void setWb(boolean isWb) {
	    this.isWb = isWb;
	}
	
	public void setCall(boolean isCall) {
	    this.isCall = isCall;
	}
	
	public void setRet(boolean isRet) {
	    this.isRet = isRet;
	}
	
	public void setImmediate(boolean isImmediate) {
	    this.isImmediate = isImmediate;
	}
	
	public void setBeq(boolean isBeq) {
	    this.isBeq = isBeq;
	}
	
	public void setBgt(boolean isBgt) {
	    this.isBgt = isBgt;
	}
	
	public void setUBranch(boolean isUBranch) {
	    this.isUBranch = isUBranch;
	}
	
	public void setEnd(boolean isEnd) {
	    this.isEnd = isEnd;
	}
	
	public void setBne(boolean isBne) {
        this.isBne = isBne;
    }

    public void setBlt(boolean isBlt) {
        this.isBlt = isBlt;
    }
    
    public HashMap<String, Boolean> getALUSignals() {
    	return aluSignals;
    }
    
    public void enableALUSignal(String operation){
    	if (aluSignals.containsKey(operation)){
    		aluSignals.put(operation, true);
    	} else {
    		System.out.println("Unexpected ALU Signal");
    	}
    }
}