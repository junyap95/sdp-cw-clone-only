package test.instruction;

import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.instruction.JgeInstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JgeInstructionTest {

    @Test
    void executeSignFalseZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);
        Instruction jgeInstruction = new JgeInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        assertFalse(machine.getFlags().getSF());
        int jgeExec = jgeInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jgeExec);
    }

    @Test
    void executeSignTrueZeroFalse() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setSF(true);
        Instruction jgeInstruction = new JgeInstruction("", "f3");
        machine.getLabels().addLabel("f3", 2);
        int jgeExec = jgeInstruction.execute(machine); // return int from getSize()
        assertEquals(1, jgeExec);
    }
}
