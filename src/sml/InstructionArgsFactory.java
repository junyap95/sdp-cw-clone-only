package sml;

import java.util.Optional;

/**
 * This class provides relevant destination or source for any Instructions.
 * This is a Singleton class that will be used by Instruction auxiliary abstract classes,
 * see {@link sml.instruction.InstructionWithDestAndSrc}, {@link sml.instruction.InstructionWithSourceOnly}, {@link sml.instruction.InstructionWithAddress}
 */
public class InstructionArgsFactory {
    private final Machine machine;
    private static InstructionArgsFactory instance = null; // WARNING: not thread safe

    private InstructionArgsFactory(Machine machine) {
        this.machine = machine;
    }

    // method - get the only instance of factory
    public static InstructionArgsFactory getInstructionFactory(Machine machine) {
        if (instance == null) instance = new InstructionArgsFactory(machine);
        return instance;
    }

    /**
     * This method assumes that if the line of instruction contains a destination, it must have "," in the format
     *
     * @param line input string e.g. "AX, 1"
     * @return an instance of InstructionDestination
     */
    public InstructionDestination getInstructionDestination(String line) {
        if (!line.contains(",")) return null;
        String destination = line.split(", ")[0].trim();
        return getDestination(destination, this.machine);
    }

    /**
     * This method assumes that if the line of instruction contains a source, it can have "," in the format
     * @param line input string e.g. "AX, 1" or "AX"
     * @return an instance of InstructionSource
     */
    public InstructionSource getInstructionSource(String line) {
        if (line.contains(",")) {
            String source = line.split(", ")[1].trim();
            return getSource(source, this.machine);
        }
        return getSource(line.trim(), this.machine);
    }

    /**
     * This method is for jump instructions where there is no InstructionDestination or InstructionSource needed
     * @param line input string e.g. "f3"
     * @return string e.g. "f3"
     */
    public String getInstructionAddress(String line) {
        return line.trim();
    }

    // method - migrated from Translator
    private InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty().or(() -> OperandImmediate.parseOperandImmediate(s)).or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory())).or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters())).or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters())).orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    // method - migrated from Translator
    private InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty().or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory())).or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters())).or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters())).orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }
}
