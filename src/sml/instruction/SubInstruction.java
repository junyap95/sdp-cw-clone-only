package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;
import java.util.Objects;

public class SubInstruction extends InstructionAuxiliary {

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue(); // 1
        int res = result.getValue();
        result.setValue(res - value);
        return getSize();
    }

}