package test.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.OperandMemory;
import sml.OperandRegister;
import sml.instruction.DivInstruction;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.*;

class DivInstructionTest {
    @Test
    void executeReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 30);
        OperandRegister operandRegister = new OperandRegister(CX, machine.getRegisters());
        Instruction divInstruction = new DivInstruction("", operandRegister);
        divInstruction.execute(machine);
        assertEquals(3, machine.getRegisters().get(AX));
        assertEquals(10, machine.getRegisters().get(DX));
        assertEquals(1 + operandRegister.getSize(), divInstruction.getSize());
    }

    @Test
    void executeMem() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getMemory().set(1, 40);
        OperandMemory operandMemory = new OperandMemory(1, machine.getMemory());
        Instruction divInstruction = new DivInstruction("", operandMemory);
        divInstruction.execute(machine);
        assertEquals(2, machine.getRegisters().get(AX));
        assertEquals(20, machine.getRegisters().get(DX));
        assertEquals(1 + operandMemory.getSize(), divInstruction.getSize());
    }

}