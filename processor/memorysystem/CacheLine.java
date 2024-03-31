package processor.memorysystem;

public class CacheLine {
	
	int tag;
	
	int[] data;
	
	boolean valid_bit;
	
	int LRU_Counter;
	
	public CacheLine(int tag, int[] data) {
		
		this.tag = tag;
		this.data = data;
		this.valid_bit = true;
		this.LRU_Counter = Integer.MAX_VALUE;
		
	}
	
	public boolean isValid() {
		return valid_bit;
	}
	
	public int getLRU_value() {
		return LRU_Counter;
	}
	
	public void decrementLRU() {
		LRU_Counter -= 1;
	}
	
}
