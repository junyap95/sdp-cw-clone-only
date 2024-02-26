package sml.instruction;

import sml.Instruction;

import java.util.Objects;

public abstract class InstructionWithAddress extends Instruction {
    protected final String address;

    public InstructionWithAddress(String label, String opcode, String address) {
        super(label, opcode);
        this.address = address;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + this.address;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        InstructionWithAddress that = (InstructionWithAddress) object;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
