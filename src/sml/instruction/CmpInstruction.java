package sml.instruction;

import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class CmpInstruction extends InstructionWithDestAndSrc {
    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue(); // 1
        int res = destination.getValue();
        m.getFlags().setZF(value == res);
        m.getFlags().setSF(res < value);
        return getSize();
    }
}
