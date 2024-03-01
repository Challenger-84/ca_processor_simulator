package processor.pipeline;

import generic.ControlSignals;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int instruction;
	int immx;
	int branchTarget;
	int op1;
	int op2;
	ControlSignals control;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public int getImmx() {
		return immx;
	}

	public void setImmx(int immx) {
		this.immx = immx;
	}

	public int getBranchTarget() {
		return branchTarget;
	}

	public void setBranchTarget(int branchTarget) {
		this.branchTarget = branchTarget;
	}

	public int getOp1() {
		return op1;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public int getOp2() {
		return op2;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	public ControlSignals getControl() {
		return control;
	}

	public void setControl(int op2) {
		this.control = control;
	}

}
