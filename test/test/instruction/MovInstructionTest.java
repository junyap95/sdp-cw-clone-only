package test.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static sml.InstructionArgsFactory.getInstructionFactory;
import static sml.Registers.RegisterNameImpl.*;

class MovInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // Reference: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 75);
        String line = "CX, AX";
        Instruction movInstruction = (Instruction) context.getBean("mov", "", line, getInstructionFactory(machine));
        int result = movInstruction.execute(machine);
        assertEquals(1, result);
        assertEquals(75, machine.getRegisters().get(CX));
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 10);

        String line = "CX, 50";
        Instruction movInstruction = (Instruction) context.getBean("mov", "", line, getInstructionFactory(machine));
        int result = movInstruction.execute(machine);
        assertEquals(2, result);
        assertEquals(50, machine.getRegisters().get(CX));
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);

        String line = "CX, [1]";
        Instruction movInstruction = (Instruction) context.getBean("mov", "", line, getInstructionFactory(machine));
        int result = movInstruction.execute(machine);
        assertEquals(2, result);
        assertEquals(100, machine.getRegisters().get(CX));
    }

}