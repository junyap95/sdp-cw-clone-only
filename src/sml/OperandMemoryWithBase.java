package sml;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperandMemoryWithBase implements InstructionSource, InstructionDestination {
    private final int offset;
    private final Memory memory;
    private final RegisterName base;
    private final Registers registers;

    private static final Pattern pattern = Pattern.compile("^\\[(\\p{Alpha}+)(\\+(\\d+))?]$");

    public static Optional<OperandMemoryWithBase> parseOperandMemoryWithBase(String s, Memory memory, Registers registers) {
        boolean b = pattern.matcher(s).find();

        Matcher m = pattern.matcher(s);
        if (m.find()) {
            int offset = Optional.ofNullable(m.group(3))
                    .map(Integer::parseInt)
                    .orElse(0);

            String name = m.group(1);
            return registers.parseRegisterName(name)
                    .map(r -> new OperandMemoryWithBase(offset, memory, r, registers));
        }

        return Optional.empty();
    }

    public OperandMemoryWithBase(int offset, Memory memory, RegisterName base, Registers registers) {
        this.offset = offset;
        this.memory = memory;
        this.base = base;
        this.registers = registers;
    }

    @Override
    public int getValue() {
        return memory.get(registers.get(base) + offset);
    }

    @Override
    public void setValue(int value) {
        memory.set(registers.get(base) + offset, value);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "[" + base + " + " + offset + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OperandMemoryWithBase that = (OperandMemoryWithBase) object;
        return offset == that.offset && Objects.equals(memory, that.memory) && Objects.equals(base, that.base) && Objects.equals(registers, that.registers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, memory, base, registers);
    }
}
