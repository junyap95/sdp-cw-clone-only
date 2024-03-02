package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionArgsFactory;
import sml.InstructionSource;
import java.util.Objects;

/**
 * Represents an abstract class for instructions with both a destination and a source operand
 * <p>
 * This class extends the {@link sml.Instruction} class and provides common functionality
 */
public abstract class InstructionWithDestAndSrc extends Instruction {
    protected final InstructionDestination destination;
    protected final InstructionSource source;

    public InstructionWithDestAndSrc(String label, String opcode, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, opcode);
        this.destination = instructionArgsFactory.getInstructionDestination(line);
        this.source = instructionArgsFactory.getInstructionSource(line);
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
        if (object == null || this.getClass() != object.getClass()) return false;
        InstructionWithDestAndSrc other = (InstructionWithDestAndSrc) object;
        return Objects.equals(destination, other.destination) && Objects.equals(source, other.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, source);
    }
}
