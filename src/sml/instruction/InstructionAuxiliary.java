package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

import java.util.Objects;

public abstract class InstructionAuxiliary extends Instruction {
    protected final InstructionDestination result;
    protected final InstructionSource source;
    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     * @param result
     * @param source
     */
    public InstructionAuxiliary(String label, String opcode, InstructionDestination result, InstructionSource source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + result.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        InstructionAuxiliary other = (InstructionAuxiliary) object;
        return Objects.equals(result, other.result) && Objects.equals(source, other.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, source);
    }
}
