package sml.instruction;

import sml.Machine;

public class JneInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jne";

    public JneInstruction(String label, String address) {
        super(label, OP_CODE, address);
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
