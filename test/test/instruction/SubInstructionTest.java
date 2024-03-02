package test.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.*;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.InstructionArgsFactory.getInstructionFactory;
import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class SubInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // code referred from: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeRegToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 150);

        String line = "CX, AX";
        Instruction subInstruction = (Instruction) context.getBean("sub", "", line, getInstructionFactory(machine));
        subInstruction.execute(machine);
        assertEquals(50, machine.getRegisters().get(CX));
        assertEquals(100, machine.getRegisters().get(AX));
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 250);

        String line = "CX, 100";
        Instruction subInstruction = (Instruction) context.getBean("sub", "", line, getInstructionFactory(machine));
        subInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 140);

        String line = "CX, [1]";
        Instruction subInstruction = (Instruction) context.getBean("sub", "", line, getInstructionFactory(machine));
        subInstruction.execute(machine);
        assertEquals(40, machine.getRegisters().get(CX));
    }
}
