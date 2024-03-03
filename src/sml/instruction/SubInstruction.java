package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

/**
 * This class represents a subtract instruction
 */
public class SubInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    /**
     * Subtracts the contents of destination with source and store the result in destination
     *
     * @param m the machine the instruction runs on
     * @return size of the instruction
     */
    @Override
    public int execute(Machine m) {
        int src = source.getValue();
        int dest = destination.getValue();
        destination.setValue(dest - src);
        return getSize();
    }

}