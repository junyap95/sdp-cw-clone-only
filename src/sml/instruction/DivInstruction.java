package sml.instruction;

import sml.InstructionArgsFactory;
import sml.Machine;
import sml.Registers;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.DX;

public class DivInstruction extends InstructionWithSourceOnly{
    public static final String OP_CODE = "div";

    public DivInstruction(String label, String line, InstructionArgsFactory instructionArgsFactory) {
        super(label, OP_CODE, line, instructionArgsFactory);
    }

    @Override
    public int execute(Machine m) {
        // First combine the upper and lower 32 bits from register AX and DX using OR operator
        // Store the division result and remainder in appropriate registers
        Registers registers = m.getRegisters();
        long valueDX = registers.get(DX) & 0xFFFFFFFFL;
        long valueAX = registers.get(AX) & 0xFFFFFFFFL;
        long combined = valueDX << 32 | valueAX;
        long divisionResult = combined / source.getValue();
        long divisionRemainder = combined % source.getValue();
        registers.set(AX, (int) divisionResult);
        registers.set(DX, (int) divisionRemainder);
        return getSize();
    }
}
