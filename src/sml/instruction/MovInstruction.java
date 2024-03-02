package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

public class MovInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    public int execute(Machine m) {
        int value = source.getValue();
        destination.setValue(value);
        return getSize();
    }

}
