package sml;

/**
 * Represents an abstract instruction.
 * An instance that extends this class will inherit
 *      - label
 *      - opcode
 */
public abstract class Instruction {
    protected final String label;
    protected final String opcode;

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label optional label (can be null)
     * @param opcode operation name
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    public String getLabel() {
        return label;
    }

    public String getOpcode() {
        return opcode;
    }

    public abstract int getSize();

    /**
     * Executes the instruction in the given machine.
     *
     * @param machine the machine the instruction runs on
     * @return the new program counter
     */
    public abstract int execute(Machine machine);

    protected String getLabelString() {
        return (getLabel() == null) ? "" : getLabel() + ": ";
    }

    // Abstract keyword here  means that the toString method must be implemented by the subclass of this abstract class
    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
