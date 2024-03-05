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
		this.control_unit = new ControlUnit();
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			int currentPC = IF_OF_Latch.getPC();
			int instruction = IF_OF_Latch.getInstruction();
			String inst_str = Integer.toBinaryString(instruction);
			String inst_string = inst_str;
			if (inst_str.length() != 32) {
				inst_string = "0".repeat(32-inst_str.length()) + inst_str;
			}
			
			//5bit to control unit 
			int opcode = Integer.parseInt(inst_string.substring(0,5), 2);
			ControlSignals control = control_unit.getControlSignals(opcode);
			
			//imm operands
			//immx
			String immx_18 = inst_string.substring(15);
			String immx_32;
			if(immx_18.charAt(0)== '0'){
				immx_32 = "0".repeat(32-17) + immx_18;
			}
			else{
				immx_32 = "1".repeat(32-17) + immx_18;
			} 
			int immx = Integer.parseUnsignedInt(immx_32, 2);
	
			//branchTarget
			int offset;
			int rd = Integer.parseInt(inst_string.substring(15,20));
			if (control.isUBranch()) {
				String offset_str = inst_string.substring(10);
				String offset_32;
				if(offset_str.charAt(0)== '0'){
					offset_32 = "0".repeat(10) + offset_str;
				}
				else{
					offset_32 = "1".repeat(10) + offset_str;
				} 
				offset = Integer.parseUnsignedInt(offset_32, 2);
				offset += rd;
			} else {
				offset = immx;
			}
//			System.out.println("offset22 " + (offset_22));
//			System.out.println("offset32 " + (offset_32));
//			System.out.println(offset);
			//add pc and offset then store in OF_EX latch
			int branchTarget = offset + currentPC;
//			String branch_targeString = Integer.toBinaryString(branch_target_value);
//			if (branch_targeString.length() != 32) {
//				branch_targeString = '0'*(32-branch_targeString.length()) + branch_targeString;
//			}
//			int branchTarget = Integer.parseInt(branch_targeString);
			
		    //reg operands
			String rs1String = inst_string.substring(5,10);
			String rs2String = inst_string.substring(10,15);
			String rdString = inst_string.substring(15,20);

			int rp1;
			int rp2;

			rp1 = Integer.parseUnsignedInt(rs1String, 2);
			rp2 = Integer.parseUnsignedInt(rs2String, 2);
			
			int op1 = containingProcessor.getRegisterFile().getValue(rp1);
			int op2 = containingProcessor.getRegisterFile().getValue(rp2);

			//stop processor add below !!!!!
			if (control.isEnd()) {
				Simulator.setSimulationComplete(true);
			}

			
			OF_EX_Latch.setPC(IF_OF_Latch.getPC());
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp2(op2);
			OF_EX_Latch.setBranchTarget(branchTarget);
			OF_EX_Latch.setImmx(immx);
			OF_EX_Latch.setControl(control);
			OF_EX_Latch.setInstruction(instruction);
			
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}
	
}
