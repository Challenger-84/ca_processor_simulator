package processor.pipeline;

import generic.ControlSignals;
import processor.Processor;
import generic.Statistics;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	ControlUnit control_unit;
	IF_EnableLatchType IF_EnableLatch;
	EX_OF_LatchType EX_OF_Latch;
	
	boolean operand_locked = false;

	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, IF_EnableLatchType iF_EnableLatch, EX_OF_LatchType eX_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.EX_OF_Latch = eX_OF_Latch;
		
		this.control_unit = new ControlUnit();
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			// OF is set to busy if EX is busy or waiting for MA
			IF_OF_Latch.setOFBusy(OF_EX_Latch.isEXBusy() || OF_EX_Latch.isEXWaiting());

			if (IF_OF_Latch.isOFBusy()) {
				OF_EX_Latch.setEX_enable(true);
				IF_EnableLatch.setIF_enable(false);
				return;
			}
			
			if (EX_OF_Latch.isBranchTaken() || IF_OF_Latch.isNop()) {
				sendNop();
				EX_OF_Latch.setBranchTaken(false);
				return;
			}
			
			int currentPC = IF_OF_Latch.getPC();
			int instruction = IF_OF_Latch.getInstruction();
			
			
			String inst_string = Integer.toBinaryString(instruction);
			if (inst_string.length() != 32) {
				inst_string = "0".repeat(32-inst_string.length()) + inst_string;
			}
			
			//5bit to control unit 
			int opcode = Integer.parseInt(inst_string.substring(0,5), 2);
			
			// get control signals from control unit
			ControlSignals control = control_unit.getControlSignals(opcode);
			
			//imm operations
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
			int rd = Integer.parseInt(inst_string.substring(5,10), 2);


			//jmp, offset = rd+imm
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
				if(containingProcessor.getRegisterLock(rd) == 0){
					offset += containingProcessor.getRegisterFile().getValue(rd);
				}else{
					offset = 0;
				}

			} else {
				offset = immx;
			}

			int branchTarget = offset + currentPC;
			
		    //reg operands
			String rs1String = inst_string.substring(5,10);
			String rs2String = inst_string.substring(10,15);

			int rs1 = Integer.parseUnsignedInt(rs1String, 2);
			int rs2 = Integer.parseUnsignedInt(rs2String, 2);
			int op1;
			int op2;
			
			if (operand_locked) {
				Statistics stats = new Statistics();
				stats.incrementNumOfDataHazards(1);
				sendNop();
				if(containingProcessor.getRegisterLock(rs1) == 0 && (control.isImmediate() || containingProcessor.getRegisterLock(rs2) == 0)){
					operand_locked = false;
				}
				return;
			}
			
			int rd_address;
			String rdString;
			if (control.isImmediate()) {
				rdString = inst_string.substring(10,15);
			} else {
				rdString = inst_string.substring(15,20);
			}
			rd_address = Integer.parseInt(rdString, 2);

			
			boolean checkImm = control.isImmediate() && !control.isSt(); // If it is a Store instruction we still need to check if rd is not locked
			
			if(containingProcessor.getRegisterLock(rs1) == 0 && (checkImm || containingProcessor.getRegisterLock(rs2) == 0)){
				op1 = containingProcessor.getRegisterFile().getValue(rs1);
				op2 = containingProcessor.getRegisterFile().getValue(rs2);
				
				// Setting the next Latch
				OF_EX_Latch.setPC(IF_OF_Latch.getPC());
				OF_EX_Latch.setOp1(op1);
				OF_EX_Latch.setOp2(op2);
				OF_EX_Latch.setBranchTarget(branchTarget);
				OF_EX_Latch.setImmx(immx);
				OF_EX_Latch.setControl(control);
				OF_EX_Latch.setInstruction(instruction);
				
				IF_EnableLatch.setIF_enable(true);
				IF_OF_Latch.setOF_enable(false);
			}
			else{
				operand_locked = true;
				
				sendNop();
				Statistics stats = new Statistics();
				stats.incrementNumOfDataHazards(1);
				IF_EnableLatch.setIF_enable(false);//disable IF unit
				IF_OF_Latch.setOF_enable(true);
				
				OF_EX_Latch.setEX_enable(true);
				
				return;

			}


			
			// Locking rd
			if(rd_address != 0 && control.isWb()){
				containingProcessor.lockRegister(rd_address);
			}
			
			// Locking x31 if it is a divide instruction
			if (control.getALUSignals().get("div")) {
				containingProcessor.lockRegister(31);
			}
			
			
			// If end disable IF
			if (control.isEnd()) {
				IF_EnableLatch.setIF_enable(false);//disable IF unit
			}
			
			OF_EX_Latch.setNop(false);
			OF_EX_Latch.setEX_enable(true);
			
		}
	}
	
	private void sendNop() {
		
		OF_EX_Latch.setPC(IF_OF_Latch.getPC());
		OF_EX_Latch.setOp1(0);
		OF_EX_Latch.setOp2(0);
		OF_EX_Latch.setBranchTarget(0);
		OF_EX_Latch.setImmx(0);
		OF_EX_Latch.setControl(control_unit.getControlSignals(0));
		OF_EX_Latch.setInstruction(0);
		OF_EX_Latch.setNop(true);
		
		OF_EX_Latch.setEX_enable(true);
	}
	
}
