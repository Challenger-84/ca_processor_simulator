package processor.memorysystem;

import configuration.Configuration;
import generic.CacheReadEvent;
import generic.CacheResponseEvent;
import generic.CacheWriteEvent;
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
import generic.MemoryWriteEvent;


public class Cache implements Element{
	
	Processor containingProcessor;
	private CacheLine[] cache_array;
	int LRU_decrement_interval = 20;
	
	public Cache(Processor containingProcessor, int size) {
		this.containingProcessor = containingProcessor;
		cache_array = new CacheLine[size];
		for (int i = 0; i < size; i++) { 
			cache_array[i] = new CacheLine(0, new int[] {0}, false);
		}
	}
	
	private int[] searchCache(int maddr) {
		
		/* 
		 * This function returns an int array where the first element is either 1 or 0 indicating if the given memory address was present or not,
		 * and the second element is the index of the memory address in cache (if present).
		 */
		
		//TODO: Generalize for any line size (Right now it's for line size of 1)
		
		int[] result = {0, 0};
		
		for (int i = 0; i < cache_array.length; i++) {
			if (cache_array[i].tag == maddr && cache_array[i].isValid()) {
				result = new int[] {1, i};
			}
		}

		return result;
	}
	
	private void HandleCacheMiss(int tag, Element reqElement) {
		
		Simulator.getEventQueue().addEvent( new MemoryReadEvent(
				Clock.getCurrentTime() + Configuration.mainMemoryLatency, 
				this, 
				containingProcessor.getMainMemory(), 
				tag,
				reqElement
			));
		
	}
	
	public void handleEvent(Event e) {
		
		if (e.getEventType() == EventType.MemoryResponse) {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			
			CacheLine new_line = new CacheLine(event.getAddr(), new int []{event.getValue()}, true);
			int empty_index = getEmptyCacheLine();
			if (empty_index >= 0) {
				cache_array[empty_index] = new_line;
			} else {
				cache_array[LRU()] = new_line;				
			}
			
			Simulator.getEventQueue().addEvent(new CacheResponseEvent(
					Clock.getCurrentTime(),
					this,
					event.getRequestingUnit(),
					getCacheLine(searchCache(event.getAddr())[1]).data[0],
					event.getAddr(),
					false
					));
		}
		else if (e.getEventType() == EventType.DecrementLRU) {
			updateLRU();
		}
		else if (e.getEventType() == EventType.CacheRead){
			CacheReadEvent event = (CacheReadEvent) e;
			HandleCacheRead(event);
		}
		else if (e.getEventType() == Event.EventType.CacheWrite) {
			CacheWriteEvent event = (CacheWriteEvent) e;
			HandleCacheWrite(event);
		}
		
		return;
	}
	
	
	private int getEmptyCacheLine() {
		/*
		 * This function returns the index of an empty cacheline, if it doesn't exist it returns -1
		 */
		
		int index = -1;
			
		for (int i = 0; i < cache_array.length; i++) {
			if (!cache_array[i].isValid()){
				index = i;
				break;
			}
		}
		
		return index;
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
	
	public void updateLRU() {
		/*
		 * This function decrements the LRU of all the cache lines
		 */
		for (CacheLine cache_line : cache_array) {
			cache_line.decrementLRU();
		}
		
		Simulator.getEventQueue().addEvent(new DecrementLRUEvent(
				Clock.getCurrentTime() + LRU_decrement_interval,
				this,
				this
				));
	}
	
	private CacheLine getCacheLine(int index) {
		if (index < cache_array.length)
		{
			cache_array[index].setLRU();
			return cache_array[index];			
			
		} else {
			System.out.println("Invalid index provided" + index);
			return cache_array[0];
		}
	}

	public void HandleCacheRead(CacheReadEvent event) {
		if(searchCache(event.getAddressToReadFrom())[0] == 1){
			Simulator.getEventQueue().addEvent(new CacheResponseEvent(
						Clock.getCurrentTime(),
						this,
						event.getRequestingElement(),
						getCacheLine(searchCache(event.getAddressToReadFrom())[1]).data[0],
						event.getAddressToReadFrom(),
						false
						));
		}
		else{
			HandleCacheMiss(event.getAddressToReadFrom(), event.getRequestingElement());
		}
	}

	public void HandleCacheWrite(CacheWriteEvent event){
		if(searchCache(event.getAddressToWriteTo())[0] == 1){
			//index from searchCache, if present
			setCacheLine(searchCache(event.getAddressToWriteTo())[1], event.getValue());
			Simulator.getEventQueue().addEvent(new CacheResponseEvent(
						Clock.getCurrentTime(),
						this,
						event.getRequestingElement(),
						0,
						event.getAddressToWriteTo(),
						true
						));
		}
		else{
			//index from lru ,if not presnet
			setCacheLine(LRU(), event.getValue());
			Simulator.getEventQueue().addEvent(new CacheResponseEvent(
						Clock.getCurrentTime(),
						this,
						event.getRequestingElement(),
						0,
						event.getAddressToWriteTo(),
						true
						));
		}
		//TODO Send MemoryWriteEvent
	}

	private void setCacheLine(int tag, int value){
		cache_array[tag].data[0] = value;
		cache_array[tag].setLRU();
	}

}
