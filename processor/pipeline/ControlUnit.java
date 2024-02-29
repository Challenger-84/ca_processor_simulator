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
		
		for (OperationType operation : immOperations) {
			if (operation == op) {
				signals.setImmediate(true);
				op = operator[opcode - 1];
			}
		}

		switch (op) {
		    case add:
		        signals.enableALUSignal("add");
	
		    case sub:
		        signals.enableALUSignal("sub");
		        break;

		    case mul:
		        signals.enableALUSignal("mul");
		        break;

		    case div:
		        signals.enableALUSignal("div");
		        break;

		    case and:
		        signals.enableALUSignal("and");
		        break;

		    case or:
		        signals.enableALUSignal("or");
		        break;

		    case xor:
		        signals.enableALUSignal("xor");
		        break;

		    case slt:
		        signals.enableALUSignal("slt");
		        break;

		    case sll:
		        signals.enableALUSignal("sll");
		        break;

		    case srl:
		        signals.enableALUSignal("srl");
		        break;

		    case sra:
		        signals.enableALUSignal("sra");
		        break;
	
		    case load:
		        signals.setLd(true);
		        break;
	
		    case store:
		        signals.setSt(true);
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
