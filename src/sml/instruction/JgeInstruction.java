package sml.instruction;

import sml.*;

public class JgeInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jge";

    public JgeInstruction(String label, String address) {
        super(label, OP_CODE, address);
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
