package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;

public class JneInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jne";

    public JneInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);

    }

    @Override
    public int execute(Machine m) {
        boolean ZF = m.getFlags().getZF();
        if(!ZF) {
            return m.getOffset(this.address);
        }
        return getSize();
    }
}
