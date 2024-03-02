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

class AddInstructionTest {
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
        machine.getRegisters().set(AX, 75);
        machine.getRegisters().set(CX, 75);

        String line = "CX, AX";
        Instruction addInstruction = (Instruction) context.getBean("add", "", line, getInstructionFactory(machine));
        addInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
        assertEquals(75, machine.getRegisters().get(AX));
    }

    @Test
    void executeImmToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getRegisters().set(CX, 50);

        String line = "CX, 100";
        Instruction addInstruction = (Instruction) context.getBean("add", "", line, getInstructionFactory(machine));
        addInstruction.execute(machine);
        assertEquals(150, machine.getRegisters().get(CX));
    }

    @Test
    void executeMemToReg() {
        Machine machine = new Machine(0x40_000);
        machine.getMemory().set(1, 100);
        machine.getRegisters().set(CX, 40);

        String line = "CX, [1]";
        Instruction addInstruction = (Instruction) context.getBean("add", "", line, getInstructionFactory(machine));
        addInstruction.execute(machine);
        assertEquals(140, machine.getRegisters().get(CX));
    }

}