package sml.instruction;

import sml.*;

public class JgeInstruction extends Instruction {
    public static final String OP_CODE = "jge";
    String address;

    public JgeInstruction(String label, String address) {
        super(label, OP_CODE);
        this.address = address;
    }

    @Override
    public int getSize() {
        return 1;
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

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + this.address;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
