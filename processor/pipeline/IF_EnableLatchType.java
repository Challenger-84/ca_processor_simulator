package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	boolean IF_Busy;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	
	public boolean isIFBusy() {
		return IF_Busy;
	}

	public void setIFBusy(boolean iF_Busy) {
		IF_Busy = iF_Busy;
	}

}
