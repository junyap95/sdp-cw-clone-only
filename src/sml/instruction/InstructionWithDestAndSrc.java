package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import java.util.Objects;

public abstract class InstructionWithDestAndSrc extends Instruction {
    protected final InstructionDestination destination;
    protected final InstructionSource source;

    public InstructionWithDestAndSrc(String label, String opcode, InstructionDestination destination, InstructionSource source) {
        super(label, opcode);
        this.destination = destination;
        this.source = source;
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + destination.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + destination + ", " + source;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        InstructionWithDestAndSrc other = (InstructionWithDestAndSrc) object;
        return Objects.equals(destination, other.destination) && Objects.equals(source, other.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, source);
    }
}
