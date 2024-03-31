package processor.memorysystem;

import javax.sound.sampled.Line;

import configuration.Configuration;
import generic.DecrementLRUEvent;
import generic.Element;
import processor.Clock;
import processor.Processor;
import generic.Simulator;

// Imports regarding events
import generic.Event;
import generic.Event.EventType;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;


public class Cache implements Element{
	
	Processor containingProcessor;
	CacheLine[] cache_array;
	int LRU_decrement_interval = 20;
	
	public Cache(Processor containingProcessor, int size) {
		this.containingProcessor = containingProcessor;
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
		
		Simulator.getEventQueue().addEvent( new MemoryReadEvent(
				Clock.getCurrentTime() + Configuration.mainMemoryLatency, 
				this, 
				containingProcessor.getMainMemory(), 
				tag 
			));
		
	}
	
	public void handleEvent(Event e) {
		
		EventType eventType = e.getEventType();
		if (eventType == EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			
			CacheLine new_line = new CacheLine(event.getAddr(), new int []{event.getValue()});
			int empty_index = getEmptyCacheLine();
			if (empty_index >= 0) {
				cache_array[empty_index] = new_line;
			} else {
				cache_array[LRU()] = new_line;				
			}
		}
		else if (eventType == EventType.DecrementLRU) {
			updateLRU();
		}
		
		return;
	}
	
	private int LRU() {
		/*
		 * This function returns the index in cache_array with the least LRU (that is least recently used)
		 */
		
		int index = 0;
		int min_val = 0;
		for (int i = 0; i < cache_array.length; i++) {
			int lru = cache_array[i].getLRU_value();
			if (min_val < lru) {
				min_val = lru;
				index = i;
			}
		}
		
		return index;
	}
	
	private int getEmptyCacheLine() {
		/*
		 * This function returns the index of an empty cacheline, if it doesn't exist it returns -1
		 */
		
		int index = -1;
			
		for (int i = 0; i < cache_array.length; i++) {
			if (cache_array[i].isValid()){
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	public void updateLRU() {
		for (CacheLine cache_line : cache_array) {
			cache_line.decrementLRU();
		}
		
		Simulator.getEventQueue().addEvent(new DecrementLRUEvent(
				Clock.getCurrentTime() + LRU_decrement_interval,
				this,
				this
				));
	}
}
