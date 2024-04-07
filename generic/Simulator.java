package generic;

import processor.Clock;
import processor.Processor;
import generic.Statistics;
import generic.EventQueue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.DataInputStream;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	static EventQueue eventQueue;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		eventQueue = new EventQueue();
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
				
		// Setting up the Register File
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

		
		// Reading the .obj file and putting all the instructions in memory
		File file = new File(assemblyProgramFile);
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			// Getting the PC value and setting PC to it
			int pc_val = dis.readInt();
			processor.getRegisterFile().setProgramCounter(pc_val);
			
			int index = 0;
			while (true) {
				int value = dis.readInt();
				processor.getMainMemory().setWord(index, value);
		        index++;
			}
	    
		} catch (EOFException eof) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void simulate()
	{
		
		
		while(simulationComplete == false)
		{
			System.out.println("Cycle : " + (Clock.getCurrentTime() + 1 + "----------------------------------"));
			processor.getIFUnit().performBranch();
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			
		}
		
		// TODO
		// set statistics
		Statistics stats = new Statistics();
		stats.setNumberOfCycles((int) Clock.getCurrentTime());
		stats.setNumberOfInstructions((int) processor.getRWUnit().getNumofInstructions());
		stats.incrementNumberOfNops((int) processor.getRWUnit().getNumofNops());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
	
	public static EventQueue getEventQueue() {
		return eventQueue;
	}

}
