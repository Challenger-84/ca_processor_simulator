package processor.pipeline;

import java.util.HashMap;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO
		
		EX_MA_Latch.setMA_enable(true);
		OF_EX_Latch.setEX_enable(false);
	}

	
	private int ArithmeticLogicUnit(HashMap<String, Boolean> signal, int op1, int op2) {
		
		// Get the operation that needs to be performed
		if (signal.get("add")) {
            return op1 + op2;
        }
        if (signal.get("sub")) {
            return op1 - op2;
        }
        if (signal.get("mul")) {
            return op1 * op2;
        }
        if (signal.get("div")) {
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
