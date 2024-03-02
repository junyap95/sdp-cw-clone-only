package test.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.InstructionArgsFactory;
import sml.Machine;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static sml.InstructionArgsFactory.getInstructionFactory;


class JneInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeZeroFalse() {
        Machine machine = new Machine(0x40_000);
        String line = "f3";
        Instruction jneInstruction = (Instruction) context.getBean("jne", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2);
        assertFalse(machine.getFlags().getZF());
        int jneExec = jneInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jneExec);
    }

    @Test
    void executeZeroTrue() {
        Machine machine = new Machine(0x40_000);
        String line = "f3";
        machine.getFlags().setZF(true);
        Instruction jneInstruction = (Instruction) context.getBean("jne", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2);
        int jneExec = jneInstruction.execute(machine); // return int from getSize()
        assertEquals(1, jneExec);
    }
}