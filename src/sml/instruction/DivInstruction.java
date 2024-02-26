package sml.instruction;

import sml.InstructionSource;
import sml.Machine;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.DX;

public class DivInstruction extends InstructionWithSourceOnly{

    public DivInstruction(String label, String opcode, InstructionSource source) {
        super(label, opcode, source);
    }

    @Override
    public int execute(Machine machine) {
        long valueDX = machine.getRegisters().get(DX) & 0xFFFFFFFFL;
        long valueAX = machine.getRegisters().get(AX) & 0xFFFFFFFFL;

        long combined = valueDX << 32 | valueAX;

        long divisionResult = combined / source.getValue();
        long divisionRemainder = combined % source.getValue();

        machine.getRegisters().set(AX, (int) divisionResult);
        machine.getRegisters().set(DX, (int) divisionRemainder);
        return getSize();
    }
}
