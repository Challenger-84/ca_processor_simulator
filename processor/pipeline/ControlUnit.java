package processor.pipeline;

import generic.Instruction.OperationType;
import generic.ControlSignals;

public class ControlUnit {

	OperationType operator[] = {OperationType.add, OperationType.addi, OperationType.sub, OperationType.subi, 
			OperationType.mul, OperationType.muli, OperationType.div, OperationType.divi, 
			OperationType.and, OperationType.andi, OperationType.or, OperationType.ori, 
			OperationType.xor, OperationType.xori, OperationType.slt, OperationType.slti, 
			OperationType.sll, OperationType.slli, OperationType.srl, OperationType.srli, 
			OperationType.sra, OperationType.srai, OperationType.load, OperationType.store, 
			OperationType.jmp, OperationType.beq, OperationType.bne, OperationType.blt, 
			OperationType.bgt, OperationType.end};
	
	OperationType[] immOperations = {
		    OperationType.addi, OperationType.subi, OperationType.muli, OperationType.divi,
		    OperationType.andi, OperationType.ori, OperationType.xori, OperationType.slti,
		    OperationType.slli, OperationType.srli, OperationType.srai
		};
	

	public ControlSignals getControlSignals(int opcode) {
		
		OperationType op = operator[opcode];
		
		ControlSignals signals = new ControlSignals();
		
		// Checking for alu operations ending with i
		for (OperationType operation : immOperations) {
			if (operation == op) {
				signals.setImmediate(true);
				op = operator[opcode - 1];
			}
		}

		switch (op) {
		    case add:
		        signals.enableALUSignal("add");
		        signals.setWb(true);
		        break;
	
		    case sub:
		        signals.enableALUSignal("sub");
		        signals.setWb(true);
		        break;

		    case mul:
		        signals.enableALUSignal("mul");
		        signals.setWb(true);
		        break;

		    case div:
		        signals.enableALUSignal("div");
		        signals.setWb(true);
		        break;

		    case and:
		        signals.enableALUSignal("and");
		        signals.setWb(true);
		        break;

		    case or:
		        signals.enableALUSignal("or");
		        signals.setWb(true);
		        break;

		    case xor:
		        signals.enableALUSignal("xor");
		        signals.setWb(true);
		        break;

		    case slt:
		        signals.enableALUSignal("slt");
		        signals.setWb(true);
		        break;

		    case sll:
		        signals.enableALUSignal("sll");
		        signals.setWb(true);
		        break;

		    case srl:
		        signals.enableALUSignal("srl");
		        signals.setWb(true);
		        break;

		    case sra:
		        signals.enableALUSignal("sra");
		        signals.setWb(true);
		        break;
	
		    case load:
		        signals.setLd(true);
		        signals.setWb(true);
		        signals.enableALUSignal("add");
		        signals.setImmediate(true);
		        break;
	
		    case store:
		        signals.setSt(true);
		        signals.enableALUSignal("add");
		        break;
	
		    case jmp:
		        signals.setUBranch(true);
		        break;
	
		    case beq:
		        signals.setBeq(true);
		        break;
	
		    case bne:
		        signals.setBne(true);
		        break;
	
		    case blt:
		        signals.setBlt(true);
		        break;
	
		    case bgt:
		        signals.setBgt(true);
		        break;
	
		    case end:
		        signals.setEnd(true);
		        break;
		        
		    default:
		    	System.out.println("Unexpected Operation Type!");
		    	break;
	}


    return signals;
	}
}
