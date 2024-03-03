package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

/**
 * This class represents an addition instruction
 */
public class AddInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "add";

    public AddInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    /**
     * Add the contents of destination with source and store the result in destination
     *
     * @param m the machine the instruction runs on
     * @return size of the instruction
     */
    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        int res = destination.getValue();
        destination.setValue(res + value);
        return getSize();
    }
}
