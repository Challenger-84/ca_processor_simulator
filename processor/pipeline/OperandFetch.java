package processor.pipeline;

import generic.ControlSignals;
import processor.Processor;
import generic.Simulator;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	ControlUnit control_unit;

	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int instruction = IF_OF_Latch.getInstruction();
			String inst_string = Integer.toBinaryString(instruction);

			//5bit to control unit 
			int opcode = Integer.parseInt(inst_string.substring(0,5));
			ControlSignals control = control_unit.getControlSignals(opcode);
			
			//imm operands
			//immx
			String immx_18 = inst_string.substring(14);
			String immx_32;
			if(immx_18.charAt(0)== '0'){
				immx_32 = '0'*(32-18) + immx_18;
			}
			else{
				immx_32 = '1'*(32-18) + immx_18;
			} 
			int immx = Integer.parseInt(immx_32);
			OF_EX_Latch.setImmx(immx);
			
			//branchTarget
			String offset_27 = inst_string.substring(5);
			String offset_32;
			if(offset_27.charAt(0)== '0'){
				offset_32 = '0'*(32-27) + offset_27;
			}
			else{
				offset_32 = '1'*(32-27) + offset_27;
			} 
			int offset = Integer.parseInt(offset_32);

			//add pc and offset then store in OF_EX latch
			int branch_target_value = Integer.parseUnsignedInt(offset_32, 2) + currentPC;
			String branch_targeString = Integer.toBinaryString(branch_target_value);
			if (branch_targeString.length() != 32) {
				branch_targeString = '0'*(32-branch_targeString.length()) + branch_targeString;
			}
			int branchTarget = Integer.parseInt(branch_targeString);
			OF_EX_Latch.setBranchTarget(branchTarget);
			
		    //reg operands
			String rs1String = inst_string.substring(10,15);
			String rs2String = inst_string.substring(15,20);
			String rdString = inst_string.substring(5,10);

			int rp1;
			int rp2;

			rp1 = Integer.parseInt(rs1String);

			if(control.isSt() == false){
				rp2 = Integer.parseInt(rs2String);
			}
			else{
				rp2 =  Integer.parseInt(rdString);
			}

			int op1 = containingProcessor.getRegisterFile().getValue(rp1);
			int op2 = containingProcessor.getRegisterFile().getValue(rp2);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp2(op2);

			//stop processor add below !!!!!
			if (control.isEnd()) {
				Simulator.setSimulationComplete(true);
			}

			
			OF_EX_Latch.setPC(IF_OF_Latch.getPC());
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}
	
}
