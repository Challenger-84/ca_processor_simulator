package processor.pipeline;

public class EX_IF_LatchType {
	
	private boolean IF_enable;
	
	private int branchTarget;
	private boolean BranchTaken;
	
	public EX_IF_LatchType()
	{
		IF_enable = false;
		
		branchTarget = 0;
		BranchTaken = false;
	}
	
	public boolean isIF_enable() {
		return IF_enable;
	}
	
	public void setIF_enable(boolean enable) {
		IF_enable = enable;
	}
	
	public boolean isBranchTaken() {
		return BranchTaken;
	}
	
	public void set_BranchTaken(boolean val) {
		BranchTaken = val;
	}
	
	public int branchTarget() {
		return branchTarget;
	}
	
	public void set_branchTarget(int target) {
		branchTarget = target;
	}

}
