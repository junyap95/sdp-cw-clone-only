package test.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.OperandImmediate;
import sml.OperandRegister;
import sml.instruction.CmpInstruction;
import sml.instruction.JneInstruction;
import sml.instruction.MulInstruction;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

class JneInstructionTest {

    @Test
    void executeZeroFalse() {
        Machine machine = new Machine(0x40_000);
        Instruction jneInstruction = new JneInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        assertFalse(machine.getFlags().getZF());
        int jneExec = jneInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jneExec);
    }

    @Test
    void executeZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);
        Instruction jneInstruction = new JneInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        int jneExec = jneInstruction.execute(machine); // return int from getSize()
        assertEquals(1, jneExec);
    }
}