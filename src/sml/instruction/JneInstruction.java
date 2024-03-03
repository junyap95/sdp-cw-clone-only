package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

/**
 * This class represents a "jump if greater than or equal to" instruction
 * boolean conditions: ZF=false
 */
public class JneInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jne";

    public JneInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);

    }

    /**
     * @param m the machine the instruction runs on
     * @return the address of an instruction to jump to if boolean conditions met
     * otherwise the size of instruction
     */
    @Override
    public int execute(Machine m) {
        boolean ZF = m.getFlags().getZF();
        if (!ZF) return m.getOffset(this.address);
        return getSize();
    }
}
