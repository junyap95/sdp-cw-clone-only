package sml.instruction;

import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class MulInstruction extends InstructionAuxiliary {
    public static final String OP_CODE = "mul";

    public MulInstruction(String label,InstructionDestination destination, InstructionSource source) {
        super(label, OP_CODE, destination, source);
    }

    @Override
    public int getSize() {
        return 1 + source.getSize();
    }

    @Override
    public int execute(Machine m) {
        int valueAX = result.getValue();
        int valueCX = source.getValue();
        result.setValue(valueAX * valueCX);
        return getSize();
    }

}
