package processor.pipeline;

import java.util.HashMap;
import generic.ControlSignals;
import generic.Statistics;

import processor.Processor;


public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	EX_OF_LatchType EX_OF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch, EX_OF_LatchType eX_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_OF_Latch = eX_OF_Latch;
	}
	
	public void performEX()
	{
		if (OF_EX_Latch.isEX_enable()) {
			
			System.out.println("EX Ins: " + OF_EX_Latch.getInstruction());
			
			ControlSignals control = OF_EX_Latch.getControl();
			
			// Getting the operands
			int op1 = OF_EX_Latch.getOp1();
			
			// Checking if we should use immediate or not
			int op2;
			if (control.isImmediate()) {
				op2 = OF_EX_Latch.getImmx();
			} else {
				op2 = OF_EX_Latch.getOp2();
			}
			
			if (control.isLd()) {
				op2 = OF_EX_Latch.getImmx();
			} else if (control.isSt()) {
				op1 = OF_EX_Latch.getOp2();
				op2 = OF_EX_Latch.getImmx();
			}
			
			// Setting BranchTarget
			EX_IF_Latch.setbranchTarget(OF_EX_Latch.getBranchTarget());
			
			EX_IF_Latch.setBranchTaken(false);
			EX_OF_Latch.setBranchTaken(false);
			
			// Setting isBranchTaken
			Statistics stats = new Statistics();
			if (control.isBeq() || control.isBgt() || control.isBlt() || control.isBne() || control.isUBranch()) {
				stats.incrementNumOfBranch(1);
			}
			
			if (control.isUBranch()) {
				EX_IF_Latch.setBranchTaken(true);
				EX_OF_Latch.setBranchTaken(true);
			} else {
				
				if (control.isBeq() && (op1 == op2)) {
					EX_IF_Latch.setBranchTaken(true);
					EX_OF_Latch.setBranchTaken(true);
				} 
				else if (control.isBgt() && (op1 > op2)) {
					EX_IF_Latch.setBranchTaken(true);
					EX_OF_Latch.setBranchTaken(true);
				} else if (control.isBlt() && (op1 < op2)) {
					EX_IF_Latch.setBranchTaken(true);
					EX_OF_Latch.setBranchTaken(true);
				} else if (control.isBne() && (op1 != op2)) {
					EX_IF_Latch.setBranchTaken(true);
					EX_OF_Latch.setBranchTaken(true);
				}
			}
			
			if (EX_IF_Latch.isBranchTaken() == true) {
				stats.incrementNumOfBranchTaken(1);
			}
			
			EX_IF_Latch.setIF_enable(true);
			
			EX_MA_Latch.setALUResult(ArithmeticLogicUnit(control.getALUSignals(), op1, op2));
			
			// Pass other data to next latch
			EX_MA_Latch.setstoreVal(OF_EX_Latch.getOp1());
			EX_MA_Latch.setPC(OF_EX_Latch.getPC());
			EX_MA_Latch.setInstruction(OF_EX_Latch.getInstruction());
			EX_MA_Latch.setControlSignals(control);
	
			
			EX_MA_Latch.setMA_enable(true);
			OF_EX_Latch.setEX_enable(false);
		}
	}

	
	private int ArithmeticLogicUnit(HashMap<String, Boolean> signal, int op1, int op2) {
		
		// Get the operation that needs to be performed
		if (signal.get("add")) {
			long long_op1 = (long) op1;
			long long_op2 = (long) op2;
            long result =  long_op1 + long_op2;
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            	containingProcessor.getRegisterFile().setValue(31, 1);  // Set x31 to 1 in case of overflow
            }
            return (int) result;
            
        }
        if (signal.get("sub")) {
        	long long_op1 = (long) op1;
			long long_op2 = (long) op2;
            long result =  long_op1 - long_op2;
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            	containingProcessor.getRegisterFile().setValue(31, 1);  // Set x31 to 1 in case of overflow
            }
            return (int) result;
        }
        if (signal.get("mul")) {
        	long long_op1 = (long) op1;
			long long_op2 = (long) op2;
            long result =  long_op1 * long_op2;
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            	containingProcessor.getRegisterFile().setValue(31, 1);  // Set x31 to 1 in case of overflow
            }
            return (int) result;
        }
        if (signal.get("div")) {
        	containingProcessor.getRegisterFile().setValue(31, op1 % op2);    // Setting the remainder to x31 register
            return op1/op2;
        }
        if (signal.get("and")) {
        	return op1 & op2;
        }
        if (signal.get("or")) {
        	return op1 | op2;
        }
        if (signal.get("xor")) {
        	return op1 ^ op2;
        }
        if (signal.get("slt")) {
        	if (op1 < op2) {
        		return 1;
        	} else {
        		return 0;
        	}
        }
        if (signal.get("sll")) {
        	return op1 << op2;
        }
        if (signal.get("srl")) {
        	return op1 >>> op2;
        }
        if (signal.get("sra")) {
        	return op1 >> op2;
        }

		return 0;
	}
	
}
