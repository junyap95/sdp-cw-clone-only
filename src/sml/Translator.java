package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import static sml.InstructionArgsFactory.getInstructionFactory;

/**
 * This class reads the lines from a .sml file and interprets them as individual instructions,
 * and then store the information in the machine's program
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName = fileName;
    }

    public void readAndTranslate(Machine machine) throws IOException {
        Labels labels = machine.getLabels();
        Map<Integer, Instruction> program = machine.getProgram();

        // translate the small program in the file into
        // the labels and the program

        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();
            int programCounter = 0;

            // each iteration processes the contents of line
            // and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label, machine);
                if (instruction != null) {
                    if (label != null) labels.addLabel(label, programCounter);
                    program.put(programCounter, instruction);
                    programCounter += instruction.getSize();
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label, Machine machine) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (line.isEmpty()) return null;
        String opcode = scan();
        var context = new ClassPathXmlApplicationContext("/beans.xml");
        return (Instruction) context.getBean(opcode, label, this.line, getInstructionFactory(machine));
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":")) return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        int whiteSpacePosition = 0;
        while (whiteSpacePosition < line.length()) {
            if (Character.isWhitespace(line.charAt(whiteSpacePosition))) break;
            whiteSpacePosition++;
        }

        String word = line.substring(0, whiteSpacePosition);
        line = line.substring(whiteSpacePosition);

        return word;
    }
}

