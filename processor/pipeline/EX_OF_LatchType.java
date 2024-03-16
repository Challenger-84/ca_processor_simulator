package processor.pipeline;

public class EX_OF_LatchType {


	private boolean BranchTaken;
	
	public EX_OF_LatchType()
	{
		BranchTaken = false;
	}

	public boolean isBranchTaken() {
		return BranchTaken;
	}
	
	public void setBranchTaken(boolean val) {
		BranchTaken = val;
	}
	
}
