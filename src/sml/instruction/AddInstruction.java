package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

public class AddInstruction extends InstructionWithDestAndSrc {

    public static final String OP_CODE = "add";

    public AddInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        int res = destination.getValue();
        destination.setValue(res + value);
        return getSize();
    }
}
