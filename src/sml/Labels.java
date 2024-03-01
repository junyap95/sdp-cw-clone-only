package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Represents a set of labels for a machine with their corresponding addresses
 */
public final class Labels {
    private final Map<String, Integer> labels = new HashMap<>();

    /**
     * Adds a label with the associated address to the map.
     *
     * @param label the label
     * @param address the address the label refers to
     */
    public void addLabel(String label, int address) {
        Objects.requireNonNull(label);
        if (labels.containsKey(label)) throw new IllegalArgumentException("Duplicate label found: " + label);
        // An exception should be thrown when there is duplicate labels.
        // This is to ensure each label in the map is unique and prevent overwrites that may result in inconsistencies.
        labels.put(label, address);
    }

    /**
     * Returns the address associated with the label.
     *
     * @param label the label
     * @return the address the label refers to
     */
    public int getAddress(String label) {
        Integer address = labels.get(label);
        if (address == null) throw new IllegalArgumentException("Label cannot be found: " + label);
        // A NullPointerException can be thrown when label does not exist in the hashmap
        return address;
    }

    /**
     * representation of this instance,
     * in the form "[label -> address, label -> address, ..., label -> address]"
     * Note: using entrySet() provides direct access to both keys and values in a single iteration,
     * which is more efficient than using keySet() that requires accessing the map twice: once to retrieve the value corresponding to each key,
     * and again to retrieve the keys for sorting.
     * @return the string representation of the labels map
     */
    @Override
    public String toString() {
        return labels.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Labels labels1 = (Labels) object;
        return Objects.equals(labels, labels1.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    /**
     * Removes the labels
     */
    public void reset() {
        labels.clear();
    }
}
