package sml.instruction;

import sml.Instruction;
import sml.InstructionSource;
import java.util.Objects;

/**
 * Represents an abstract class for instructions with both a destination and a source operand
 * <p>
 * This class extends the {@link sml.Instruction} class and provides common functionality
 */
public abstract class InstructionWithSourceOnly extends Instruction {
    protected final InstructionSource source;

    /**
     * Constructor: an instruction with a destination and source
     *
     * @param source The source operand of the instruction.
     */
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
        if (object == null || this.getClass() != object.getClass()) return false;
        InstructionWithSourceOnly other = (InstructionWithSourceOnly) object;
        return Objects.equals(source, other.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash( source);
    }
}
