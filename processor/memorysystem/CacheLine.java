package processor.memorysystem;

public class CacheLine {
	
	int tag;
	
	int[] data;
	
	boolean valid_bit;
	
	int LRU_Counter;
	
	public CacheLine(int tag, int[] data, boolean valid_bit) {
		
		this.tag = tag;
		this.data = data;
		this.valid_bit = valid_bit;
		this.LRU_Counter = Integer.MAX_VALUE;
		
	}
	
	public void setValid() {
		valid_bit = true;
	}
	
}
