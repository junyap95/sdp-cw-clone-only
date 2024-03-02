package test.instruction;

import org.junit.jupiter.api.Test;
import sml.*;
import sml.instruction.MovInstruction;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.*;

class MovInstructionTest {

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandRegister operandRegisterAX = new OperandRegister(AX, machine.getRegisters());
        Instruction movInstruction = new MovInstruction("", operandRegisterCX, operandRegisterAX);
        movInstruction.execute(machine);
        assertEquals(100, machine.getRegisters().get(CX));
        assertEquals(100, machine.getRegisters().get(AX));
        assertEquals(1, movInstruction.getSize());
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 10);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandImmediate operandImmediate = new OperandImmediate(100);
        Instruction movInstruction = new MovInstruction("", operandRegisterCX, operandImmediate);
        movInstruction.execute(machine);
        assertEquals(100, machine.getRegisters().get(CX));
        assertEquals(1 + operandImmediate.getSize() + operandRegisterCX.getSize(), movInstruction.getSize());
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandMemory operandMemory = new OperandMemory(1, machine.getMemory());
        Instruction movInstruction = new MovInstruction("", operandRegisterCX, operandMemory);
        movInstruction.execute(machine);
        assertEquals(100, machine.getRegisters().get(CX));
        assertEquals(1 + operandMemory.getSize() + operandRegisterCX.getSize(), movInstruction.getSize());
    }

}