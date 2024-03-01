package sml.instruction;

import sml.Machine;

/**
 * Represents a "jle" (Jump if Less than or Equal) instruction in the Simple Machine Language (SML).
 * This instruction extends the {@link InstructionWithAddress} class.
 * <p>
 * When executed, if the Zero Flag (ZF) or Sign Flag (SF) in the machine's flags are set,
 * the program counter is set to the address specified in the instruction.
 * Otherwise, the program counter is incremented by the size of the instruction.
 */
public class JleInstruction extends InstructionWithAddress {
    public static final String OP_CODE = "jle";

    public JleInstruction(String label, String address) {
        super(label, OP_CODE, address);
    }

    @Override
    public int execute(Machine m) {
        boolean ZF = m.getFlags().getZF();
        boolean SF = m.getFlags().getSF();
        if(ZF || SF) {
            return m.getOffset(this.address);
        }
        return getSize();
    }
}
