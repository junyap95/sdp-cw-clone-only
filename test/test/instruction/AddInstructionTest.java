package test.instruction;

import org.junit.jupiter.api.Test;
import sml.*;
import sml.instruction.AddInstruction;
import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

class AddInstructionTest {

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 50);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandRegister operandRegisterAX = new OperandRegister(AX, machine.getRegisters());
        Instruction addInstruction = new AddInstruction("", operandRegisterCX, operandRegisterAX);
        addInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
        assertEquals(100, machine.getRegisters().get(AX));
        assertEquals(1 + operandRegisterAX.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 50);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandImmediate operandImmediate = new OperandImmediate(100);
        Instruction addInstruction = new AddInstruction("", operandRegisterCX, operandImmediate);
        addInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
        assertEquals(1 + operandImmediate.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 40);
        OperandRegister operandRegisterCX = new OperandRegister(CX, machine.getRegisters());
        OperandMemory operandMemory = new OperandMemory(1, machine.getMemory());
        Instruction addInstruction = new AddInstruction("", operandRegisterCX, operandMemory);
        addInstruction.execute(machine);
        assertEquals(140, machine.getRegisters().get(CX));
        assertEquals(1 + operandMemory.getSize() + operandRegisterCX.getSize(), addInstruction.getSize());
    }

}