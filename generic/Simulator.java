package generic;

import processor.Clock;
import processor.Processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.DataInputStream;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
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
			processor.getIFUnit().performIF();
			processor.getOFUnit().performOF();
			processor.getEXUnit().performEX();
			processor.getMAUnit().performMA();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			
			simulationComplete = true;
		}
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
