package sml.instruction;

import sml.*;

public class JgeInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jge";

    public JgeInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    @Override
    public int execute(Machine m) {
        boolean ZF = m.getFlags().getZF();
        boolean SF = m.getFlags().getSF();
        if(ZF || !SF) {
            return m.getOffset(this.address);
        }
        return getSize();
    }
}
