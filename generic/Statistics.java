package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int numberOfNops;
	
	
	static int numOfBranch = 0;
	static int numOfBranchTaken = 0;

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of nop instructions = " + numberOfNops);
			
			writer.println("Number of Branch Statements = " + numOfBranch);
			writer.println("Number of Branch Taken = " + numOfBranchTaken);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public void incrementNumberOfNops(int numberOfNops) {
		Statistics.numberOfNops += numberOfNops;
	}
	
	public void incrementNumOfBranch(int increment_val) {
		Statistics.numOfBranch += increment_val;
	}
	
	public void incrementNumOfBranchTaken(int increment_val) {
		Statistics.numOfBranchTaken += increment_val;
	}
}
