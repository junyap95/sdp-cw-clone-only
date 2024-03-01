package sml.instruction;

import sml.Instruction;

import java.util.Objects;

/**
 * Represents an abstract class for instructions with an address to jump to
 * <p>
 * This class extends the {@link sml.Instruction} class and provides common functionality
 * for instructions that involve an address to jump to.
 */
public abstract class InstructionWithAddress extends Instruction {
    protected final String address;

    /**
     * Constructor: an instruction with an address
     *
     * @param address The memory address to jump to if the conditions are met.
     */
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
        if (object == null || getClass() != object.getClass()) return false;
        InstructionWithAddress that = (InstructionWithAddress) object;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
