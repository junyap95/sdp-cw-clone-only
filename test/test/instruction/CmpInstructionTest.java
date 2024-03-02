package test.instruction;

import org.junit.jupiter.api.Test;
import sml.*;
import sml.instruction.CmpInstruction;

import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class CmpInstructionTest {

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 100);

        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandRegister operandRegisterAX = new OperandRegister(AX, machine.getRegisters());
        Instruction cmpInstruction = new CmpInstruction("", operandRegisterCX, operandRegisterAX);

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(AX, 150);

        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());

        assertEquals(1 + operandRegisterAX.getSize() + operandRegisterCX.getSize(), cmpInstruction.getSize());
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(CX, 100);

        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandImmediate operandImmediate = new OperandImmediate(100);
        Instruction cmpInstruction = new CmpInstruction("", operandRegisterCX, operandImmediate);

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(CX, 50);

        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());

        assertEquals(1 + operandImmediate.getSize() + operandRegisterCX.getSize(), cmpInstruction.getSize());
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 100);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandMemory operandMemory = new OperandMemory(1, machine.getMemory());
        Instruction cmpInstruction = new CmpInstruction("", operandRegisterCX, operandMemory);

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(CX, 50);
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());

        assertEquals(1 + operandMemory.getSize() + operandRegisterCX.getSize(), cmpInstruction.getSize());
    }
}
