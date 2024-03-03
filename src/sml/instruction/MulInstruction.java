package sml.instruction;

import sml.*;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.DX;

/**
 * This class represents a multiplication instruction
 */
public class MulInstruction extends InstructionWithSourceOnly {
    public static final String OP_CODE = "mul";

    public MulInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    /**
     * Multiplies the contents of AX and source and store the results in AX and DX
     * Stores lower 32 bits result in AX and upper 32 bits result in DX
     *
     * @param m the machine the instruction runs on
     * @return size of the instruction
     */
    @Override
    public int execute(Machine m) {
        // Cast values obtained from register AX and source to long (64 bits) and perform multiplication
        // Use shift operator on result to obtain its upper 32 bits and store it in register DX with int casting
        Registers registers = m.getRegisters();
        long valueAX = m.getRegisters().get(AX);
        long valueCX = source.getValue();
        long mulResult = valueAX * valueCX;
        registers.set(AX, (int) (mulResult));
        registers.set(DX, (int) (mulResult >> 32));
        return getSize();
    }
}
