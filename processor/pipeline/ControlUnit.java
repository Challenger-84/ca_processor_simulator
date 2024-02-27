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
	

	public ControlSignals getControlSignals(int opcode) {
		
		OperationType op = operator[opcode];
		
		ControlSignals signals = new ControlSignals();
		
		switch (op) {
        case OperationType.load:
            signals.setLd(true);
            break;

        case OperationType.store:
            signals.setSt(true);
            break;

        case OperationType.jmp:
            signals.setUBranch(true);
            break;

        case OperationType.beq:
        	signals.setBeq(true);
            break;

        case OperationType.bne:
            signals.setBne(true);
            break;

        case OperationType.blt:
            signals.setBlt(true);
            break;

        case OperationType.bgt:
            signals.setBgt(true);
            break;

        case OperationType.end:
            signals.setEnd(true);
            break;
            
        default:
        	break;
    }

    return signals;
	}
}
