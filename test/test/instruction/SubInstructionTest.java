package test.instruction;

import org.junit.jupiter.api.Test;
import sml.*;
import sml.instruction.SubInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class SubInstructionTest {

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 150);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandRegister operandRegisterAX = new OperandRegister(AX, machine.getRegisters());
        Instruction addInstruction = new SubInstruction("", operandRegisterCX, operandRegisterAX);
        addInstruction.execute(machine);
        assertEquals(50, machine.getRegisters().get(CX));
        assertEquals(100, machine.getRegisters().get(AX));
        assertEquals(1 + operandRegisterAX.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 250);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandImmediate operandImmediate = new OperandImmediate(100);
        Instruction addInstruction = new SubInstruction("", operandRegisterCX, operandImmediate);
        addInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
        assertEquals(1 + operandImmediate.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 140);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandMemory operandMemory = new OperandMemory(1, machine.getMemory());
        Instruction addInstruction = new SubInstruction("", operandRegisterCX, operandMemory);
        addInstruction.execute(machine);
        assertEquals(40, machine.getRegisters().get(CX));
        assertEquals(1 + operandMemory.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }
}
