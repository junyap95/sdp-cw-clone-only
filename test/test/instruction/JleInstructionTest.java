package test.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sml.Instruction;
import sml.InstructionArgsFactory;
import sml.Machine;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sml.InstructionArgsFactory.getInstructionFactory;

public class JleInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    // Reference: https://blog.davidehringer.com/testing/test-driven-development/unit-testing-singletons/
    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeSignTrueZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(true);

        String line = "f3";
        Instruction jleInstruction = (Instruction) context.getBean("jle", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2); // assumes label has address of 2
        int jleExec = jleInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jleExec);
    }

    @Test
    void executeSignFalseZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);

        String line = "f3";
        Instruction jleInstruction = (Instruction) context.getBean("jle", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2); // assumes label has address of 2
        int jleExec = jleInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jleExec);
    }

    @Test
    void executeSignTrueZeroFalse() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setSF(true);

        String line = "f3";
        Instruction jleInstruction = (Instruction) context.getBean("jle", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2); // assumes label has address of 2
        int jleExec = jleInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jleExec);
    }

    @Test
    void executeSignFalseZeroFalse() {
        Machine machine = new Machine(0x40_000);

        String line = "f3";
        Instruction jleInstruction = (Instruction) context.getBean("jle", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2); // assumes label has address of 2
        int jleExec = jleInstruction.execute(machine); // return int from getSize(), as flags conditions not met
        assertEquals(1, jleExec);
    }
}
