package test.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.*;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static sml.InstructionArgsFactory.getInstructionFactory;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class CmpInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // Reference: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeRegToRegEqual() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 100);

        String line = "CX, AX";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }

    @Test
    void executeRegToRegSmallerThan() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(AX, 150);
        machine.getRegisters().set(CX, 100);

        String line = "CX, AX";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertTrue(machine.getFlags().getSF());
    }

    @Test
    void executeRegToRegLargerThan() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(AX, 50);
        machine.getRegisters().set(CX, 100);

        String line = "CX, AX";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }


    @Test
    void executeImmToRegEqual() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 100);

        String line = "CX, 100";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }

    @Test
    void executeImmToRegSmallerThan() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 50);

        String line = "CX, 100";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertTrue(machine.getFlags().getSF());
    }

    @Test
    void executeImmToRegLargerThan() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 150);

        String line = "CX, 100";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }


    @Test
    void executeMemToRegEqual() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 100);

        String line = "CX, [1]";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }

    @Test
    void executeMemToRegSmallerThan() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 50);

        String line = "CX, [1]";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertTrue(machine.getFlags().getSF());
    }

    @Test
    void executeMemToRegLargerThan() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 150);

        String line = "CX, [1]";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertFalse(machine.getFlags().getZF());
        assertFalse(machine.getFlags().getSF());
    }
}
