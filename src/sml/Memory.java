package sml;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents the memory of a computer system.
 * <p>
 * Cells are accessed using integer addresses.
 */
public class Memory {
    // The contents of the memory stored in an array of integers
    private final int[] contents;
    // Each bit is a memory cell
    private final BitSet usedCells = new BitSet();

    public Memory(int size) {
        this.contents = new int[size];
    }

    // [offset] refers to the cell with address offset
    // where offset is an integer
    public int get(int address) {
        usedCells.set(address);
        return contents[address];
    }

    /**
     * Sets the value of the memory cell at the specified address.
     * Updates usedCells.
     *
     * @param address The address of the memory cell to set the value for.
     * @param value   The value to set in the memory cell.
     */
    public void set(int address, int value) {
        usedCells.set(address);
        contents[address] = value;
    }

    @Override
    public String toString() {
        return usedCells.stream()
                .mapToObj(i -> "[" + i + "] = " + contents[i])
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Memory memory = (Memory) object;
        return Arrays.equals(contents, memory.contents) && Objects.equals(usedCells, memory.usedCells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(usedCells);
        result = 31 * result + Arrays.hashCode(contents);
        return result;
    }
}
