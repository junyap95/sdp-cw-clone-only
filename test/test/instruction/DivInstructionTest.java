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

class DivInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // Reference: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 30);

        String line = "CX";
        Instruction divInstruction = (Instruction) context.getBean("div", "", line, getInstructionFactory(machine));
        int result = divInstruction.execute(machine);
        assertEquals(1, result);
        assertEquals(3, machine.getRegisters().get(AX));
        assertEquals(10, machine.getRegisters().get(DX));
    }

    @Test
    void executeMem() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getMemory().set(1, 30);

        String line = "[1]";
        Instruction divInstruction = (Instruction) context.getBean("div", "", line, getInstructionFactory(machine));
        int result = divInstruction.execute(machine);
        assertEquals(2, result);
        assertEquals(3, machine.getRegisters().get(AX));
        assertEquals(10, machine.getRegisters().get(DX));
    }

}