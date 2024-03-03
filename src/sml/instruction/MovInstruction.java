package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

/**
 * This class represents a copy instruction
 */
public class MovInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    /**
     * Moves the contents of the source to destination
     *
     * @param m the machine the instruction runs on
     * @return size of the instruction
     */
    public int execute(Machine m) {
        int value = source.getValue();
        destination.setValue(value);
        return getSize();
    }
}
