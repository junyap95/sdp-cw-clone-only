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

class MulInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // Reference: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeSmallInteger() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, 100);
        machine.getRegisters().set(CX, 2);
        String line = "CX";

        Instruction mulInstruction = (Instruction) context.getBean("mul", "", line, getInstructionFactory(machine));
        int result = mulInstruction.execute(machine);
        assertEquals(1, result);
        assertEquals(200, machine.getRegisters().get(AX));
        assertEquals( 0, machine.getRegisters().get(DX));
    }

    @Test
    void executeMaxInteger() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(AX, Integer.MAX_VALUE);
        machine.getRegisters().set(DX, 0);
        machine.getRegisters().set(CX, 2);

        String line = "CX";
        Instruction mulInstruction = (Instruction) context.getBean("mul", "", line, getInstructionFactory(machine));
        long valueAX = machine.getRegisters().get(AX);
        long valueCX = machine.getRegisters().get(CX);
        long mulResult = valueAX * valueCX;
        int mulResultUpper = (int) (mulResult >> 32);
        int result = mulInstruction.execute(machine);
        assertEquals(1, result);
        assertEquals((int) (valueCX * valueAX), machine.getRegisters().get(AX));
        assertEquals(mulResultUpper, machine.getRegisters().get(DX));
    }
}