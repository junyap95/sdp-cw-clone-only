package test.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.instruction.JleInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JleInstructionTest {

    @Test
    void executeSignTrueZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(true);
        Instruction jleInstruction = new JleInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        int jleExec = jleInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jleExec);
    }

    @Test
    void executeSignFalseZeroFalse() {
        Machine machine = new Machine(0x40_000);
        Instruction jleInstruction = new JleInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        int jleExec = jleInstruction.execute(machine); // return int from getSize()
        assertEquals(1, jleExec);
    }
}
