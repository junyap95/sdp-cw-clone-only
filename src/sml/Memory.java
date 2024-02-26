package sml;

import java.util.BitSet;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */

public class Memory {
    private final int[] contents;
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

    //TODO: implement methods .equals and .hashCode
}
