package sml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Represents a set of registers with associated values.
 */
public final class Registers {
    /**
     * Map to store the values of registers.
     * Key: RegisterNameImpl (enum representing register names)
     * Value: Integer (value of the register)
     */
    private final Map<RegisterNameImpl, Integer> registers = new HashMap<>();

    /**
     * Enum representing register names.
     */
    public enum RegisterNameImpl implements RegisterName {
        AX, BX, CX, DX, SP, BP
    }

    /**
     * Constructs a new Registers object and initialises register values to 0.
     */
    public Registers() {
        // It is generally not recommended to call methods here because constructors should focus on initialising the object's attributes, not processing.
        // Complex processing in constructor might have a negative impact on testability due to complex/ tightly coupled elements.
        // It is safe in this case because Registers is a final class which cannot be extended by other subclasses, reducing the risk of subclass instance creation inconsistency.
        // The logic of initialising the hashmap containing 6 registers with a value of 0 fits within the purpose of the class, which is what the clear() method is doing.
        // As long as the constructor logic remains simple and focused on initialising the object's state, it is acceptable in this context.
        clear();
    }

    /**
     * Set all register values to 0.
     */
    public void clear() {
        for (RegisterNameImpl register : RegisterNameImpl.values())
            registers.put(register, 0);
    }

    /**
     * Parses a register name from a string.
     *
     * @param s the string representing a register name
     * @return an optional containing the parsed register name, or empty if not found
     */
    public Optional<RegisterName> parseRegisterName(String s) {
        return Stream.of(RegisterNameImpl.values())
                .filter(r -> r.name().equals(s))
                // The method must return an Optional<RegisterName> type, but the filter stream only streams objects of type <RegisterNameImpl>.
                // The <> before the .map() call tells the compiler that the elements in the stream should be treated as <RegisterName> objects after the mapping operation.
                // Therefore, there is no change to the element r itself (r->r), only its type is treated as <RegisterName>.
                .<RegisterName>map(r -> r)
                .findAny();
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((RegisterNameImpl)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        // If register is not an instance of RegisterName, the casting of it to RegisterNameImpl will generate a ClassCastException
        // A call of the form of registers.get(() -> "NEW") is a lambda expression used on a functional interface RegisterName that returns a String "NEW"
        // The string cannot be cast into RegisterNameImpl as String is not a supertype of RegisterNameImpl.
        return registers.get((RegisterNameImpl)register);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
