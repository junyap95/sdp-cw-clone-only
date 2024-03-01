package sml;

import java.util.Objects;

/**
 * Represents the zero and sign flags in a machine
 * These flags are used to indicate the results of comparison operations in the machine.
 * Only comparison instructions can update the flags in a machine
 */
public class Flags {
    private boolean zero, sign;

    /**
     * Retrieves the value of the zero flag.
     *
     * @return true if the zero flag is set; false otherwise.
     */
    public boolean getZF() {
        return zero;
    }

    /**
     * Sets the value of the zero flag.
     *
     * @param zero The new value of the zero flag.
     */
    public void setZF(boolean zero) {
        this.zero = zero;
    }

    /**
     * Retrieves the value of the sign flag.
     *
     * @return true if the sign flag is set; false otherwise.
     */
    public boolean getSF() {
        return sign;
    }

    /**
     * Sets the value of the sign flag.
     *
     * @param sign The new value of the sign flag.
     */
    public void setSF(boolean sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Flags[" + "ZF=" + zero + ", SF=" + sign + ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Flags flags = (Flags) object;
        return zero == flags.zero && sign == flags.sign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zero, sign);
    }
}
