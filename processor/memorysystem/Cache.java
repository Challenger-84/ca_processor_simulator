package processor.memorysystem;

import generic.Element;
import generic.Event;
import generic.Event.EventType;

public class Cache implements Element{

	CacheLine[] cache_array;
	
	public Cache(int size) {
		cache_array = new CacheLine[size];
	}
	
	private int[] searchCache(int maddr) {
		
		/* 
		 * This function returns an int array where the first element is either 1 or 0 indicating if the given memory address was present or not,
		 * and the second element is the value of the memory address if present.
		 */
		
		//TODO: Generalize for any line size (Right now it's for line size of 1)
		
		int[] result = {0, 0};
		
		for (CacheLine line : cache_array) {
			if (line.tag == maddr) {
				result = new int[] {1, line.data[0]};
			}
		}
		
		return result;
	}
	
	private void HandleCacheMiss(int tag) {
		
	}
	
	public void handleEvent(Event e) {
		return;
	}
	
}
