package sml.instruction;

import sml.*;

/**
 * This class represents a "jump if greater than or equal to" instruction
 * boolean conditions: SF=false or ZF=true
 */
public class JgeInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jge";

    public JgeInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
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
        boolean SF = m.getFlags().getSF();
        if (ZF || !SF) return m.getOffset(this.address);
        return getSize();
    }
}
