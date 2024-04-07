package processor.memorysystem;

public class CacheLine {
	
	int tag;
	
	int[] data;
	
	boolean valid_bit;
	
	int LRU_Counter;
	
	public CacheLine(int tag, int[] data, boolean valid) {
		
		this.tag = tag;
		this.data = data;
		this.valid_bit = valid;
		this.LRU_Counter = Integer.MAX_VALUE;
		
	}
	
	public boolean isValid() {
		return valid_bit;
	}
	
	public int getLRU_value() {
		return LRU_Counter;
	}
	
	public void decrementLRU() {
		/*
		 * This function decrements LRU_Counter by 1 until it reaches 0
		 */
		if (LRU_Counter > 0) {
			LRU_Counter -= 1;			
		}
	}
	
	public void setLRU() {
		/*
		 * This function sets the LRU value to MAX Integer value
		 */
		LRU_Counter = Integer.MAX_VALUE;
	}
	
}
