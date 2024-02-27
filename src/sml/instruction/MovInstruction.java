package sml.instruction;

import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class MovInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, InstructionDestination destination, InstructionSource source) {
        super(label, OP_CODE, destination, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        destination.setValue(value);
        return getSize();
    }

}
