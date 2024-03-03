package sml.instruction;

import sml.Instruction;
import sml.InstructionArgsFactory;

import java.util.Objects;

/**
 * Represents an abstract class for instructions with an address to jump to
 * <p>
 * This class extends the {@link sml.Instruction} class and provides common functionality
 * for instructions that involve an address to jump to.
 */
public abstract class InstructionWithAddress extends Instruction {
    protected final String address;

    public InstructionWithAddress(String label, String opcode, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, opcode);
        this.address = instructionArgsFactory.getInstructionAddress(line);
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
        if (object == null || getClass() != object.getClass()) return false;
        InstructionWithAddress other = (InstructionWithAddress) object;
        return Objects.equals(opcode, other.opcode) && Objects.equals(label, other.label) && Objects.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
