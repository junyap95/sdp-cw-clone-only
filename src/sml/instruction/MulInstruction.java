package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

import java.util.Objects;

public class MulInstruction extends InstructionAuxiliary {

    public static final String OP_CODE = "mul";
    public static final String REG_AX = "AX";

    public MulInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        int res = result.getValue();
        result.setValue(res * value);
        return getSize();
    }

}
