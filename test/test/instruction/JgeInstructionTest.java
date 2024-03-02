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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static sml.InstructionArgsFactory.getInstructionFactory;

public class JgeInstructionTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");

    @BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = InstructionArgsFactory.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    void executeSignFalseZeroTrue() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setZF(true);

        String line = "f3";
        Instruction jgeInstruction = (Instruction) context.getBean("jge", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2);
        assertFalse(machine.getFlags().getSF());
        int jgeExec = jgeInstruction.execute(machine); // return int from getOffset()
        assertEquals(2, jgeExec);
    }

    @Test
    void executeSignTrueZeroFalse() {
        Machine machine = new Machine(0x40_000);
        machine.getFlags().setSF(true);

        String line = "f3";
        Instruction jgeInstruction = (Instruction) context.getBean("jge", "", line, getInstructionFactory(machine));
        machine.getLabels().addLabel("f3", 2);
        int jgeExec = jgeInstruction.execute(machine); // return int from getSize()
        assertEquals(1, jgeExec);
    }
}
