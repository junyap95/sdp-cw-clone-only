package sml.instruction;

import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class AddInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "add";

    public AddInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue(); // 1
        int res = destination.getValue();
        destination.setValue(res + value);
        return getSize();
    }
}
