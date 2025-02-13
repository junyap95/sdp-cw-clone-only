package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains
 *    - registers,
 *    - flags,
 *    - memory,
 *    - program counter,
 *    - labels,
 *    - program.
 *
 */
public final class Machine {

    private final Labels labels = new Labels();

    private final Map<Integer, Instruction> program = new HashMap<>();

    private final Registers registers;

    // The machine also has memory, which consists of 32-bit cells,
    // each referred to by an address: the first cell has address 0,
    // the second cell has address 1, etc.
    private final Memory memory;

    private final Flags flags;

    // The program counter; it contains the index (in program)
    // of the next instruction to be executed.
    private int programCounter = 0;


    public Machine(int memorySize) {
        this.registers = new Registers();
        this.flags = new Flags();
        this.memory = new Memory(memorySize);
    }

    /**
     * Execute the program in program, beginning at instruction 0.
     * Precondition: the program and its labels have been stored properly.
     */
    public void execute() {
        programCounter = 0;
        registers.clear();
        int programSize = program.values().stream().mapToInt(Instruction::getSize).sum();

        while (programCounter < programSize) {
            Instruction ins = program.get(programCounter);
            System.out.println("[" + programCounter + "] " + ins);
            programCounter += ins.execute(this);
        }
    }

    public Labels getLabels() {
        return this.labels;
    }

    public Map<Integer, Instruction> getProgram() {
        return this.program;
    }

    public Registers getRegisters() {
        return this.registers;
    }

    public Flags getFlags() { return this.flags; }

    public Memory getMemory() { return this.memory; }

    public int getOffset(String label) {
        return labels.getAddress(label) - programCounter;
    }

    /**
     * String representation of the program under execution.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        return program.values().stream()
                .map(Instruction::toString)
                .collect(Collectors.joining("\n"));
    }

    // https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html
    @Override
    public boolean equals(Object object) {
        if (object instanceof Machine other) {
            return Objects.equals(this.labels, other.labels)
                    && Objects.equals(this.program, other.program)
                    && Objects.equals(this.registers, other.registers)
                    && Objects.equals(this.memory, other.memory)
                    && Objects.equals(this.flags, other.flags)
                    && this.programCounter == other.programCounter;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels, program, registers, programCounter);
    }
}
