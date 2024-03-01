# ToyRISK Processor
## Documentation for our program:

### Instruction Fetch Unit:

### The Control Unit:
```Java
getControlSignals(int opcode) -> ControlSignals
```
This function takes in a opcode and returns a ControlSignals object with signals set according to the operation which the opcode represents.

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
