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

    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 100);

        String line = "CX, AX";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(AX, 150);

        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        // numbers are equal
        machine.getRegisters().set(CX, 100);

        String line = "CX, 100";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(CX, 50);

        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 100);

        String line = "CX, [1]";
        Instruction cmpInstruction = (Instruction) context.getBean("cmp", "", line, getInstructionFactory(machine));

        assertFalse(machine.getFlags().getZF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getZF());

        // first is smaller than the second
        machine.getRegisters().set(CX, 50);
        assertFalse(machine.getFlags().getSF());
        cmpInstruction.execute(machine);
        assertTrue(machine.getFlags().getSF());
    }
}
