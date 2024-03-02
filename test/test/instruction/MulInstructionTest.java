package test.instruction;

import org.junit.jupiter.api.Test;
import sml.*;
import sml.instruction.MulInstruction;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.*;

class MulInstructionTest {

    @Test
    void executeSmallInteger() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 2);
        OperandRegister operandRegister = new OperandRegister(CX, machine.getRegisters());
        Instruction mulInstruction = new MulInstruction("", operandRegister);
        mulInstruction.execute(machine);
        assertEquals(200, machine.getRegisters().get(AX));
        assertEquals( 0, machine.getRegisters().get(DX));
        assertEquals(1 + operandRegister.getSize(), mulInstruction.getSize());
    }

    @Test
    void executeMaxInteger() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, Integer.MAX_VALUE);
        machine.getRegisters().set(DX, 0);
        OperandRegister operandRegister = new OperandRegister(CX, machine.getRegisters());
        operandRegister.setValue(2);
        Instruction mulInstruction = new MulInstruction("", operandRegister);
        long valueAX = machine.getRegisters().get(AX);
        long valueCX = operandRegister.getValue();
        long mulResult = valueAX * valueCX;
        int mulResultUpper = (int) (mulResult >> 32);
        mulInstruction.execute(machine);
        assertEquals((int) (valueCX * valueAX), machine.getRegisters().get(AX));
        assertEquals(mulResultUpper, machine.getRegisters().get(DX));
        assertEquals(1 + operandRegister.getSize(), mulInstruction.getSize());
    }
}