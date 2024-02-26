package sml.instruction;

import sml.Instruction;
import sml.InstructionSource;
import java.util.Objects;

public abstract class InstructionWithSourceOnly extends Instruction {
    protected final InstructionSource source;

    public InstructionWithSourceOnly(String label, String opcode, InstructionSource source) {
        super(label, opcode);
        this.source = source;
    }

    @Override
    public int getSize() {
        return 1 + source.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        InstructionWithSourceOnly other = (InstructionWithSourceOnly) object;
        return Objects.equals(source, other.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash( source);
    }
}
