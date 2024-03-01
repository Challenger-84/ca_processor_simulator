# ToyRISK Processor
## Documentation for our program:

### Instruction Fetch Unit:

## ControlSignals Class

### Description:
The `ControlSignals` class represents the control signals used in a generic processor. It contains boolean fields to indicate the status of various control signals, such as load (`isLd`), store (`isSt`), write-back (`isWb`), etc. It also includes a HashMap to store ALU operation signals and their corresponding boolean values.

### Fields:
- `isSt`: Indicates whether the operation is a store operation.
- `isLd`: Indicates whether the operation is a load operation.
- `isWb`: Indicates whether the operation involves write-back.
- `isCall`: Indicates whether the operation is a call instruction.
- `isRet`: Indicates whether the operation is a return instruction.
- `isImmediate`: Indicates whether the operation involves an immediate value.
- `isBeq`: Indicates whether the operation is a branch if equal instruction.
- `isBgt`: Indicates whether the operation is a branch if greater than instruction.
- `isUBranch`: Indicates whether the operation is an unconditional branch instruction.
- `isEnd`: Indicates whether the operation is the end instruction.
- `isBne`: Indicates whether the operation is a branch if not equal instruction.
- `isBlt`: Indicates whether the operation is a branch if less than instruction.
- `aluSignals`: A HashMap containing ALU operation signals as keys and corresponding boolean values indicating whether the operation should be performed.

### Methods:
- **Getters**: Get methods for all the boolean fields.
- **Setters**: Set methods for all the boolean fields.
- `getALUSignals()`: Returns the HashMap containing ALU operation signals and their corresponding boolean values.
- `enableALUSignal(String operation)`: Enables the specified ALU operation signal by setting its corresponding boolean value to true. If the specified operation is not found in the HashMap, it prints a message indicating an unexpected ALU signal.

### Example Usage:
```java
ControlSignals signals = new ControlSignals();
signals.setLd(false);
signals.setSt(false);
signals.setBeq(true);
signals.setUBranch(false);
signals.enableALUSignal("mul");
```

## Arithmetic Logic Unit (ALU)

### Description:
The `ArithmeticLogicUnit` method performs arithmetic and logical operations based on the signals provided in the `signal` HashMap. The method takes two integer operands (`op1` and `op2`) and a HashMap of operation signals (`signal`). It returns the result of the operation.

### Parameters:
- `signal`: A HashMap containing operation signals as keys and corresponding boolean values indicating whether the operation should be performed.
- `op1`: The first operand of the operation.
- `op2`: The second operand of the operation.

### Return Value:
- The result of the operation performed based on the signals provided in the `signal` HashMap.

### Supported Operations:
The method supports the following operations based on the operation signals:
- Addition (`add`): Adds `op1` and `op2`.
- Subtraction (`sub`): Subtracts `op2` from `op1`.
- Multiplication (`mul`): Multiplies `op1` by `op2`.
- Division (`div`): Divides `op1` by `op2`.
- Bitwise AND (`and`): Performs bitwise AND operation between `op1` and `op2`.
- Bitwise OR (`or`): Performs bitwise OR operation between `op1` and `op2`.
- Bitwise XOR (`xor`): Performs bitwise XOR operation between `op1` and `op2`.
- Set Less Than (`slt`): Sets the result to 1 if `op1` is less than `op2`, otherwise sets it to 0.
- Shift Left Logical (`sll`): Shifts `op1` left by `op2` bits.
- Shift Right Logical (`srl`): Shifts `op1` right by `op2` bits with zero fill.
- Shift Right Arithmetic (`sra`): Shifts `op1` right by `op2` bits with sign extension.

### Exceptions:
- If division by zero is attempted (`div` operation) and `op2` is 0, an `ArithmeticException` is thrown.

### Example Usage:
```java
HashMap<String, Boolean> signal = new HashMap<>();
signal.put("add", false);
signal.put("sub", false);
signal.put("mul", true);
signal.put("div", false);
int op1 = 10;
int op2 = 5;
int result = ArithmeticLogicUnit(signal, op1, op2);
System.out.println("Result: " + result); // Will be 50
```
