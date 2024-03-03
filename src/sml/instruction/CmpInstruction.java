package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

/**
 * This class represents a compare instruction that alters the zero and sign flags in a machine
 */
public class CmpInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    /**
     * Compare the contents of source and destination and set flags ZF and SF
     *
     * @param m the machine the instruction runs on
     * @return size of instruction
     */
    @Override
    public int execute(Machine m) {
        int value = source.getValue(); // 1
        int res = destination.getValue();
        m.getFlags().setZF(value == res);
        m.getFlags().setSF(res < value);
        return getSize();
    }
}
